package com.google.protobuf;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import example.ExpectedGroup1;
import example.OriginalMessage;
import example.SimpleMessage;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

    public static final String TAG_PREFIX = "group";

    public static void main(String[] args) throws Descriptors.DescriptorValidationException, IOException {
        ArrayListMultimap<String, Descriptors.FieldDescriptor> map = SimpleMessage.getDescriptor().getFields().stream()
                .map(field -> {
                            Supplier<ArrayListMultimap<String, Descriptors.FieldDescriptor>> create = ArrayListMultimap::create;
                            return field.getOptions().getExtensionFields().entrySet().stream()
                                    .filter(it -> it.getKey().getJsonName().startsWith(TAG_PREFIX) &&
                                            it.getValue() instanceof Boolean &&
                                            (Boolean) it.getValue())
                                    .collect(create, (c, n) -> c.put(n.getKey().getJsonName(), field), Multimap::putAll);
                        }
                )
                .collect(ArrayListMultimap::create, (c, n) -> c.putAll(n), (c1, c2) -> c1.putAll(c2));
        ProtoSchemaBuilder schemaBuilder = new ProtoSchemaBuilder().setPackage("actual");
        for (String key : map.keySet()) {
            ProtoMessageDefinitionBuilder message = map.get(key).stream()
                    .collect(ProtoMessageDefinitionBuilder::new,
                            //todo change type from proto type to type
                            (c, n) -> c.addField(n.getJsonName(), n.toProto().getType()),
                            ProtoMessageDefinitionBuilder::merge);
            message.setMessageName(toTitleCase(key));
            schemaBuilder.addMessage(message);
        }
        new ProtoFileWriter(schemaBuilder.build().toProto()).writeTo("actual_output.proto");
        new ProtoFileWriter(ExpectedGroup1.getDescriptor().getFile().toProto()).writeTo("expected_output.proto");
    }

    private static String toTitleCase(String key) {
        char[] chars = key.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
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
