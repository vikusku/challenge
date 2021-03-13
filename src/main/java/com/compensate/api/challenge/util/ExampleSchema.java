package com.compensate.api.challenge.util;

public class ExampleSchema {

    public static final String PRODUCT =
            "{\n" +
            "  \"_type\":\"product\",\n" +
            "  \"id\":\"257a3e82-59c9-47c9-880a-74a1bbef8a07\",\n" +
            "  \"name\":\"Test Product\",\n" +
            "  \"properties\":{\n" +
            "    \"prop1\":\"some value\",\n" +
            "    \"prop2\":12.3,\n" +
            "    \"prop3\":1300399023,\n" +
            "    \"prop4\":false,\n" +
            "    \"prop5\":[\n" +
            "      \"one\",\n" +
            "      \"two\",\n" +
            "      \"three\"\n" +
            "    ],\n" +
            "    \"prop6\":{\n" +
            "      \"subProp1\":\"some value\",\n" +
            "      \"subProp2\":12.3,\n" +
            "      \"subProp3\":false,\n" +
            "      \"subProp4\":[\n" +
            "        \"one\",\n" +
            "        \"two\",\n" +
            "        \"three\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"created_at\":\"2020-04-29T12:50:08+00:00\",\n" +
            "  \"modified_at\":\"2020-05-29T12:50:08+00:00\",\n" +
            "  \"_links\":{\n" +
            "    \"self\":{\n" +
            "      \"href\":\"http://localhost/api/v1/products/257a3e82-59c9-47c9-880a-74a1bbef8a07\"\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static final String ALL_PRODUCTS =
            "{\n" +
            "  \"_embedded\":{\n" +
            "    \"productList\":[\n" +
            "      {\n" +
            "        \"_type\":\"product\",\n" +
            "        \"id\":\"257a3e82-59c9-47c9-880a-74a1bbef8a07\",\n" +
            "        \"name\":\"TestProduct1\",\n" +
            "        \"properties\":{\n" +
            "          \"prop1\":\"some value\",\n" +
            "          \"prop2\":12.3,\n" +
            "          \"prop3\":1300399023,\n" +
            "          \"prop4\":false,\n" +
            "          \"prop5\":[\n" +
            "            \"one\",\n" +
            "            \"two\",\n" +
            "            \"three\"\n" +
            "          ],\n" +
            "          \"prop6\":{\n" +
            "            \"subProp1\":\"some value\",\n" +
            "            \"subProp2\":12.3,\n" +
            "            \"subProp3\":false,\n" +
            "            \"subProp4\":[\n" +
            "              \"one\",\n" +
            "              \"two\",\n" +
            "              \"three\"\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        \"created_at\":\"2020-04-29T12:50:08+00:00\",\n" +
            "        \"modified_at\":\"2020-05-29T12:50:08+00:00\",\n" +
            "        \"_links\":{\n" +
            "          \"self\":{\n" +
            "            \"href\":\"http://localhost/api/v1/products/257a3e82-59c9-47c9-880a-74a1bbef8a07\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"_type\":\"product\",\n" +
            "        \"id\":\"3877ed4b-e2cb-4097-8c58-a8001c44096a\",\n" +
            "        \"name\":\"TestProduct2\",\n" +
            "        \"properties\":{\n" +
            "\n" +
            "        },\n" +
            "        \"created_at\":\"2020-04-29T12:50:08+00:00\",\n" +
            "        \"modified_at\":\"2020-05-29T12:50:08+00:00\",\n" +
            "        \"_links\":{\n" +
            "          \"self\":{\n" +
            "            \"href\":\"http://localhost/api/v1/products/3877ed4b-e2cb-4097-8c58-a8001c44096a\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"_type\":\"product\",\n" +
            "        \"id\":\"7a23ac61-178d-4f28-9b63-d977c629176d\",\n" +
            "        \"name\":\"TestProduct3\",\n" +
            "        \"properties\":{\n" +
            "\n" +
            "        },\n" +
            "        \"created_at\":\"2020-04-29T12:50:08+00:00\",\n" +
            "        \"modified_at\":\"2020-05-29T12:50:08+00:00\",\n" +
            "        \"_links\":{\n" +
            "          \"self\":{\n" +
            "            \"href\":\"http://localhost/api/v1/products/7a23ac61-178d-4f28-9b63-d977c629176d\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"_type\":\"product\",\n" +
            "        \"id\":\"60ed9741-3c8a-4b9a-adf9-3c821ca2393a\",\n" +
            "        \"name\":\"TestProduct4\",\n" +
            "        \"properties\":{\n" +
            "          \"prop1\":{\n" +
            "            \"hello\":\"world\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"created_at\":\"2020-04-29T12:50:08+00:00\",\n" +
            "        \"modified_at\":\"2020-05-29T12:50:08+00:00\",\n" +
            "        \"_links\":{\n" +
            "          \"self\":{\n" +
            "            \"href\":\"http://localhost/api/v1/products/60ed9741-3c8a-4b9a-adf9-3c821ca2393a\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"_links\":{\n" +
            "    \"first\":{\n" +
            "      \"href\":\"http://localhost/api/v1/products?page=0&size=4\"\n" +
            "    },\n" +
            "    \"prev\":{\n" +
            "      \"href\":\"http://localhost/api/v1/products?page=1&size=4\"\n" +
            "    },\n" +
            "    \"self\":{\n" +
            "      \"href\":\"http://localhost/api/v1/products?page=2&size=4\"\n" +
            "    },\n" +
            "    \"next\":{\n" +
            "      \"href\":\"http://localhost/api/v1/products?page=3&size=4\"\n" +
            "    },\n" +
            "    \"last\":{\n" +
            "      \"href\":\"http://localhost/api/v1/products?page=4&size=4\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"page\":{\n" +
            "    \"size\":4,\n" +
            "    \"totalElements\":20,\n" +
            "    \"totalPages\":5,\n" +
            "    \"number\":2\n" +
            "  }\n" +
            "}";
}
