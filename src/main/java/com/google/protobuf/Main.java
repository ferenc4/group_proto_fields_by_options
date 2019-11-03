package com.google.protobuf;

import example.ExpectedGroup1;
import example.OriginalMessage;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Descriptors.DescriptorValidationException, IOException {
        OriginalMessage.getDescriptor().getFields();

        new ProtoFileWriter(ExpectedGroup1.getDescriptor().getFile().toProto()).writeTo("expected_output.proto");
    }

    private Map<ProtoPath, Object> getLeafNodes(List<Descriptors.FieldDescriptor> fields) {
//        Stack<ProtoPath> toProcess = new Stack<>();
        Map<ProtoPath, Object> leafNodes = new TreeMap<>();
//        do {
//            ProtoPath parentPath = new ProtoPath();
//            JsonObject jObject = element.getAsJsonObject();
//            Set<String> fieldNames = jObject.keySet();
//            for (String fieldName : fieldNames) {
//                JsonElement jsonElement = jObject.get(fieldName);
//                if (jsonElement.isJsonArray()) {
//                    for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
//                        ProtoPath arrayEntry = parentPath.deepCopy().add(fieldName).add(i);
//                        toProcess.push(arrayEntry);
//                    }
//                } else if (jsonElement.isJsonObject()) {
//                    toProcess.push(parentPath.deepCopy().add(fieldName));
//                } else if (jsonElement.isJsonPrimitive()) {
//                    leafNodes.put(parentPath.deepCopy().add(fieldName), jsonElement.getAsJsonPrimitive());
//                } else if (jsonElement.isJsonNull()) {
//                    throw new NotImplementedException("Null json values are not yet supported.");
//                } else {
//                    throw new IllegalStateException(String.format("Invalid state for json element <%s>.",
//                            jsonElement.toString()));
//                }
//            }
//        } while (!toProcess.isEmpty());
        return leafNodes;
    }
}
