package com.google.protobuf;

import example.ExpectedGroup1;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static com.google.protobuf.TestCommons.readSampleData;
import static org.junit.Assert.*;

public class ProtoFileWriterTest {

    public static final String ONE_MESSAGE_PROTO = "oneMessage.proto";

    @Test
    public void getProtoFileDisplay() {
        String actual = new ProtoFileWriter(ExpectedGroup1.getDescriptor().getFile().toProto()).getProtoFileDisplay();
        String expected = readSampleData(ONE_MESSAGE_PROTO);
        assertEquals(expected, actual);
    }
}