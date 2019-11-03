package com.google.protobuf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ProtoSchemaBuilder {
    private static final Descriptors.FileDescriptor[] NO_DEPENDENCIES = new Descriptors.FileDescriptor[0];
    public static final String SUPPORTED_SYNTAX = "proto3";
    private Set<ProtoMessageDefinitionBuilder> messages = new HashSet<>();
    private String packageName;

    public ProtoSchemaBuilder addMessage(ProtoMessageDefinitionBuilder messageDefinitionBuilder) {
        messages.add(messageDefinitionBuilder);
        return this;
    }

    public Descriptors.FileDescriptor build() throws Descriptors.DescriptorValidationException {
        Supplier<DescriptorProtos.FileDescriptorProto.Builder> builderSupplier = DescriptorProtos.FileDescriptorProto::newBuilder;
        BiConsumer<DescriptorProtos.FileDescriptorProto.Builder, ProtoMessageDefinitionBuilder> addFunction = (c, messageBuilder) -> {
            DescriptorProtos.DescriptorProto message = messageBuilder.build();
            c.addMessageType(message);
        };
        DescriptorProtos.FileDescriptorProto.Builder fileDescriptorProtoBuilder = messages.stream()
                .collect(builderSupplier, addFunction, (c1, c2) -> c1.mergeFrom(c2.build()));
        fileDescriptorProtoBuilder.setPackage(packageName);
        fileDescriptorProtoBuilder.setSyntax(SUPPORTED_SYNTAX);
        return Descriptors.FileDescriptor.buildFrom(fileDescriptorProtoBuilder.build(), NO_DEPENDENCIES);
    }

    public ProtoSchemaBuilder clear() {
        messages = new HashSet<>();
        return this;
    }

    public ProtoSchemaBuilder setPackage(String packageName) {
        this.packageName = packageName;
        return this;
    }
}