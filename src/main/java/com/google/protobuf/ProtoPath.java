package com.google.protobuf;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProtoPath implements Comparable<ProtoPath> {
    private ArrayList<Identifier> jsonPath = new ArrayList<>();

    public Identifier getLast() {
        return jsonPath.get(jsonPath.size() - 1);
    }

    public Identifier popLast() {
        return jsonPath.remove(jsonPath.size() - 1);
    }

    public int length() {
        return jsonPath.size();
    }

    public boolean isEmpty() {
        return jsonPath.isEmpty();
    }


    public ProtoPath deepCopy() {
        ProtoPath protoPathObj = new ProtoPath();
        protoPathObj.jsonPath = new ArrayList<>(this.jsonPath);
        return protoPathObj;
    }

    public ProtoPath add(String element) {
        jsonPath.add(new Identifier(element));
        return this;
    }

    public ProtoPath add(int element) {
        jsonPath.add(new Identifier(element));
        return this;
    }

    public ProtoPath addAll(Collection<String> elements) {
        jsonPath.addAll(elements.stream().map(Identifier::new).collect(Collectors.toList()));
        return this;
    }

    public ProtoPath add(Collection<Integer> elements) {
        jsonPath.addAll(elements.stream().map(Identifier::new).collect(Collectors.toList()));
        return this;
    }
//
//    public JsonElement find(JsonElement root) {
//        JsonElement currentElement = root;
//        for (Identifier fieldId : jsonPath) {
//            if (Identifier.Type.OBJECT_ID.equals(fieldId.getType())) {
//                currentElement = currentElement.getAsJsonObject().get(fieldId.getStrId());
//            } else if (Identifier.Type.ARRAY_ID.equals(fieldId.getType())) {
//                currentElement = currentElement.getAsJsonArray().get(fieldId.getIntId());
//            } else {
//                throw new NotImplementedException("Scenario not implemented");
//            }
//        }
//        return currentElement;
//    }

    @Override
    public int compareTo(ProtoPath protoPath) {
        if (this.length() != protoPath.length()) return Integer.compare(this.length(), protoPath.length());
        for (int i = 0; i < this.length(); i++) {
            if (!this.jsonPath.get(i).equals(protoPath.jsonPath.get(i))) {
                return this.jsonPath.get(i).compareTo(protoPath.jsonPath.get(i));
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtoPath protoPath1 = (ProtoPath) o;
        return Objects.equals(jsonPath, protoPath1.jsonPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonPath);
    }

    static class Identifier implements Comparable<Identifier> {
        private final Integer intId;
        private final String strId;
        private final Type type;

        @Override
        public int compareTo(Identifier identifier) {
            int typeComparison = type.compareTo(identifier.type);
            if (typeComparison != 0) {
                return typeComparison;
            }
            if (type.equals(Type.OBJECT_ID)) {
                return strId.compareTo(identifier.strId);
            } else {
                return intId.compareTo(identifier.intId);
            }
        }

        enum Type {
            OBJECT_ID,
            ARRAY_ID
        }

        Identifier(String strId) {
            assert strId != null;
            this.strId = strId;
            this.intId = null;
            this.type = Type.OBJECT_ID;
        }

        Identifier(Integer intId) {
            assert intId != null;
            this.strId = null;
            this.intId = intId;
            this.type = Type.ARRAY_ID;
        }

        Integer getIntId() {
            return intId;
        }

        String getStrId() {
            return strId;
        }

        Type getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Identifier that = (Identifier) o;
            return Objects.equals(intId, that.intId) &&
                    Objects.equals(strId, that.strId) &&
                    type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(intId, strId, type);
        }
    }
}
