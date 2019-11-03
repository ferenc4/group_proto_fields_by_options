package com.google.protobuf;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ProtoFileWriter {

    private final DescriptorProtos.FileDescriptorProto protoDetails;

    public ProtoFileWriter(DescriptorProtos.FileDescriptorProto protoDetails) {
        this.protoDetails = protoDetails;
    }

    public void writeTo(String filename) throws IOException {
        String fileDisplay = getProtoFileDisplay();
        FileWriter fileWriter = new FileWriter(filename, false);
        fileWriter.write(fileDisplay);
        fileWriter.close();
    }

    public String getProtoFileDisplay() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(String.format("syntax = \"%s\";", protoDetails.getSyntax()));
        sj.add(String.format("option java_multiple_files = %s;", protoDetails.getOptions().getJavaMultipleFiles()));
        sj.add(String.format("package %s;", protoDetails.getPackage()));
        sj.add(protoDetails.getDependencyList().stream()
                .map(dependency -> String.format("import \"%s\";", dependency))
                .collect(Collectors.joining()));
        sj.add(protoDetails.getMessageTypeList().stream()
                .map(this::getMessageDisplay)
                .collect(Collectors.joining("\n")));
        return sj.toString();
    }

    private String getMessageDisplay(DescriptorProtos.DescriptorProto message) {
        StringJoiner msgSj = new StringJoiner("\n");
        msgSj.add(String.format("message %s {", message.getName()));
        String fieldsString = message.getFieldList().stream()
                .map(this::getFieldDisplay)
                .collect(Collectors.joining("\n"));
        msgSj.add(fieldsString);
        msgSj.add("}");
        return msgSj.toString();
    }

    private String getFieldDisplay(DescriptorProtos.FieldDescriptorProto field) {
        // e.g. bool group2 = 200000;
        String typeName;
        DescriptorProtos.FieldDescriptorProto.Type type = field.getType();
        if (DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE.equals(type)) {
            typeName = field.getTypeName().replaceAll("(\\.)(\\w+\\.)+", "");
        } else {
            typeName = field.getType().name().replaceAll("TYPE_", "").toLowerCase();
        }
        return String.format("\t%s %s = %d;", typeName, field.getName(), field.getNumber());
    }

}
