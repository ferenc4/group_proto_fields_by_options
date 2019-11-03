package com.google.protobuf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TestCommons {

    public static String readSampleData(String filepath) {
        return new BufferedReader(new InputStreamReader(TestCommons.class.getResourceAsStream("/" + filepath)))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    public static Descriptors.FileDescriptor getSampleSchema() throws Descriptors.DescriptorValidationException {
        ProtoSchemaBuilder schemaBuilder = new ProtoSchemaBuilder();
        schemaBuilder.addMessage(getSampleMessage("TestMessage1"));
        schemaBuilder.addMessage(getSampleMessage("TestMessage2"));
        schemaBuilder.setPackage("actual");
        return schemaBuilder.build();
    }

    public static ProtoMessageDefinitionBuilder getSampleMessage(String messageName) {
        int i = 1;
        ProtoMessageDefinitionBuilder msgDefBuilder = new ProtoMessageDefinitionBuilder();
        for (; i < 5; i++) {
            msgDefBuilder.addField("field" + i, DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING);
        }
        for (; i < 10; i++) {
            msgDefBuilder.addField("field" + i, DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32);
        }
        msgDefBuilder.setMessageName(messageName);
        return msgDefBuilder;
    }
}
