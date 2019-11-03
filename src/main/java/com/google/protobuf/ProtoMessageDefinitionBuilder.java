package com.google.protobuf;

public class ProtoMessageDefinitionBuilder {
    private DescriptorProtos.DescriptorProto.Builder messageBuilder;

    public ProtoMessageDefinitionBuilder() {
        messageBuilder = DescriptorProtos.DescriptorProto.newBuilder();
    }

    public ProtoMessageDefinitionBuilder addField(String fieldName, DescriptorProtos.FieldDescriptorProto.Type type) {
        DescriptorProtos.FieldDescriptorProto.Builder fieldBuilder = DescriptorProtos.FieldDescriptorProto.newBuilder()
                .setName(fieldName)
                .setNumber(getFieldId(fieldName))
                .setType(type);
        messageBuilder.addField(fieldBuilder.build());
        return this;
    }

    private int getFieldId(String fieldName) {
        // chance for causing a clash?
        return Math.abs(fieldName.hashCode()) % 536_870_911;
    }

    public ProtoMessageDefinitionBuilder setMessageName(String messageName) {
        messageBuilder.setName(messageName);
        return this;
    }

    private Message getMessageFromFileDescriptor(Descriptors.FileDescriptor fileDescriptor) {
        Descriptors.Descriptor messageDescriptor = fileDescriptor.findMessageTypeByName(messageBuilder.getName());
        DynamicMessage.Builder dynamicMessageBuilder = DynamicMessage.newBuilder(messageDescriptor);
        return dynamicMessageBuilder.build();
    }

    public ProtoMessageDefinitionBuilder clear() {
        messageBuilder = DescriptorProtos.DescriptorProto.newBuilder();
        return this;
    }

    public DescriptorProtos.DescriptorProto build() {
        return messageBuilder.build();
    }
}