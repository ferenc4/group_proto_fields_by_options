package com.google.protobuf;

import com.google.protobuf.Descriptors;

public class ProtoPrimitive {
    private Object value;
    private Descriptors.FieldDescriptor descriptor;

    public enum ProtoType {
        UINT32,
        MESSAGE,
        STRING
    }
}
