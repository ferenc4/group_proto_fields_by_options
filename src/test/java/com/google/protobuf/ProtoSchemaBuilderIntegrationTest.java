package com.google.protobuf;

import example.ExpectedGroup1;
import org.junit.Test;

import static com.google.protobuf.TestCommons.getSampleSchema;
import static com.google.protobuf.TestCommons.readSampleData;
import static org.junit.Assert.assertEquals;

public class ProtoSchemaBuilderIntegrationTest {

    public static final String TWO_MESSAGES_PROTO = "twoMessages.proto";

    @Test
    public void test() throws Descriptors.DescriptorValidationException {
        String actual = new ProtoFileWriter(getSampleSchema().toProto()).getProtoFileDisplay();
        String expected = readSampleData(TWO_MESSAGES_PROTO);
        assertEquals(expected, actual);
    }
}
