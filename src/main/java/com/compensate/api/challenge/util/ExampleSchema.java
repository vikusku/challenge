package com.compensate.api.challenge.util;

public class ExampleSchema {

  public static final String PRODUCT =
      "{\n" +
          "    \"_type\": \"product\",\n" +
          "    \"id\": \"351be92f-509e-4a0e-827a-e5c98ba49fc8\",\n" +
          "    \"name\": \"GrandChild\",\n" +
          "    \"properties\": {\n" +
          "        \"prop1\": \"some value\",\n" +
          "        \"prop2\": 12.3,\n" +
          "        \"prop3\": 1300399023,\n" +
          "        \"prop4\": false,\n" +
          "        \"prop5\": [\n" +
          "            \"one\",\n" +
          "            \"two\",\n" +
          "            \"three\"\n" +
          "        ]\n" +
          "    },\n" +
          "    \"created_at\": \"2021-03-14T23:03:06+02:00\",\n" +
          "    \"modified_at\": \"2021-03-14T23:03:06+02:00\",\n" +
          "    \"_links\": {\n" +
          "        \"self\": {\n" +
          "            \"href\": \"http://localhost:8080/api/v1/products/351be92f-509e-4a0e-827a-e5c98ba49fc8\"\n"
          +
          "        },\n" +
          "        \"parent\": {\n" +
          "            \"href\": \"http://localhost:8080/api/v1/products/7598c4dc-b08a-4896-a59e-29521a2b544b\"\n"
          +
          "        },\n" +
          "        \"root\": {\n" +
          "            \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "        }\n" +
          "    }\n" +
          "}";

  public static final String ALL_PRODUCTS =
      "{\n" +
          "    \"_embedded\": {\n" +
          "        \"products\": [\n" +
          "            {\n" +
          "                \"_type\": \"product\",\n" +
          "                \"id\": \"be8835b9-62ae-406f-be54-cd68f08dd1a4\",\n" +
          "                \"name\": \"Parent\",\n" +
          "                \"properties\": {\n" +
          "                    \"prop1\": \"some value\",\n" +
          "                    \"prop2\": 12.3,\n" +
          "                    \"prop3\": 1300399023,\n" +
          "                    \"prop4\": false,\n" +
          "                    \"prop5\": [\n" +
          "                        \"one\",\n" +
          "                        \"two\",\n" +
          "                        \"three\"\n" +
          "                    ],\n" +
          "                    \"prop6\": {\n" +
          "                        \"subProp1\": \"some value\",\n" +
          "                        \"subProp2\": 12.3,\n" +
          "                        \"subProp3\": false,\n" +
          "                        \"subProp4\": [\n" +
          "                            \"one\",\n" +
          "                            \"two\",\n" +
          "                            \"three\"\n" +
          "                        ]\n" +
          "                    }\n" +
          "                },\n" +
          "                \"created_at\": \"2021-03-14T23:02:13+02:00\",\n" +
          "                \"modified_at\": \"2021-03-14T23:02:13+02:00\",\n" +
          "                \"_links\": {\n" +
          "                    \"self\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"subProducts\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4/subproducts?page=0&size=10\"\n"
          +
          "                    }\n" +
          "                }\n" +
          "            },\n" +
          "            {\n" +
          "                \"_type\": \"product\",\n" +
          "                \"id\": \"a54e56fb-2cc9-4eb7-b952-c16802e8debc\",\n" +
          "                \"name\": \"Child 1\",\n" +
          "                \"properties\": {\n" +
          "                    \"prop1\": \"some value\",\n" +
          "                    \"prop2\": 12.3,\n" +
          "                    \"prop3\": 1300399023,\n" +
          "                    \"prop4\": false,\n" +
          "                    \"prop5\": [\n" +
          "                        \"one\",\n" +
          "                        \"two\",\n" +
          "                        \"three\"\n" +
          "                    ]\n" +
          "                },\n" +
          "                \"created_at\": \"2021-03-14T23:02:43+02:00\",\n" +
          "                \"modified_at\": \"2021-03-14T23:02:43+02:00\",\n" +
          "                \"_links\": {\n" +
          "                    \"self\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/a54e56fb-2cc9-4eb7-b952-c16802e8debc\"\n"
          +
          "                    },\n" +
          "                    \"parent\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"root\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    }\n" +
          "                }\n" +
          "            },\n" +
          "            {\n" +
          "                \"_type\": \"product\",\n" +
          "                \"id\": \"afde9b10-e8f0-414d-8ccc-34734ffbeec4\",\n" +
          "                \"name\": \"Child 2\",\n" +
          "                \"properties\": {\n" +
          "                    \"prop1\": \"some value\",\n" +
          "                    \"prop2\": 12.3,\n" +
          "                    \"prop3\": 1300399023,\n" +
          "                    \"prop4\": false,\n" +
          "                    \"prop5\": [\n" +
          "                        \"one\",\n" +
          "                        \"two\",\n" +
          "                        \"three\"\n" +
          "                    ]\n" +
          "                },\n" +
          "                \"created_at\": \"2021-03-14T23:02:47+02:00\",\n" +
          "                \"modified_at\": \"2021-03-14T23:02:47+02:00\",\n" +
          "                \"_links\": {\n" +
          "                    \"self\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/afde9b10-e8f0-414d-8ccc-34734ffbeec4\"\n"
          +
          "                    },\n" +
          "                    \"parent\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"root\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    }\n" +
          "                }\n" +
          "            },\n" +
          "            {\n" +
          "                \"_type\": \"product\",\n" +
          "                \"id\": \"7598c4dc-b08a-4896-a59e-29521a2b544b\",\n" +
          "                \"name\": \"Child 3\",\n" +
          "                \"properties\": {\n" +
          "                    \"prop1\": \"some value\",\n" +
          "                    \"prop2\": 12.3,\n" +
          "                    \"prop3\": 1300399023,\n" +
          "                    \"prop4\": false,\n" +
          "                    \"prop5\": [\n" +
          "                        \"one\",\n" +
          "                        \"two\",\n" +
          "                        \"three\"\n" +
          "                    ]\n" +
          "                },\n" +
          "                \"created_at\": \"2021-03-14T23:02:49+02:00\",\n" +
          "                \"modified_at\": \"2021-03-14T23:02:49+02:00\",\n" +
          "                \"_links\": {\n" +
          "                    \"self\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/7598c4dc-b08a-4896-a59e-29521a2b544b\"\n"
          +
          "                    },\n" +
          "                    \"parent\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"root\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"subProducts\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/7598c4dc-b08a-4896-a59e-29521a2b544b/subproducts?page=0&size=10\"\n"
          +
          "                    }\n" +
          "                }\n" +
          "            },\n" +
          "            {\n" +
          "                \"_type\": \"product\",\n" +
          "                \"id\": \"351be92f-509e-4a0e-827a-e5c98ba49fc8\",\n" +
          "                \"name\": \"GrandChild\",\n" +
          "                \"properties\": {\n" +
          "                    \"prop1\": \"some value\",\n" +
          "                    \"prop2\": 12.3,\n" +
          "                    \"prop3\": 1300399023,\n" +
          "                    \"prop4\": false,\n" +
          "                    \"prop5\": [\n" +
          "                        \"one\",\n" +
          "                        \"two\",\n" +
          "                        \"three\"\n" +
          "                    ]\n" +
          "                },\n" +
          "                \"created_at\": \"2021-03-14T23:03:06+02:00\",\n" +
          "                \"modified_at\": \"2021-03-14T23:03:06+02:00\",\n" +
          "                \"_links\": {\n" +
          "                    \"self\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/351be92f-509e-4a0e-827a-e5c98ba49fc8\"\n"
          +
          "                    },\n" +
          "                    \"parent\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/7598c4dc-b08a-4896-a59e-29521a2b544b\"\n"
          +
          "                    },\n" +
          "                    \"root\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    }\n" +
          "                }\n" +
          "            }\n" +
          "        ]\n" +
          "    },\n" +
          "    \"_links\": {\n" +
          "        \"first\": {\n" +
          "            \"href\": \"http://localhost:8080/api/v1/products?page=0&size=5\"\n" +
          "        },\n" +
          "        \"self\": {\n" +
          "            \"href\": \"http://localhost:8080/api/v1/products?page=0&size=5\"\n" +
          "        },\n" +
          "        \"next\": {\n" +
          "            \"href\": \"http://localhost:8080/api/v1/products?page=1&size=5\"\n" +
          "        },\n" +
          "        \"last\": {\n" +
          "            \"href\": \"http://localhost:8080/api/v1/products?page=1&size=5\"\n" +
          "        }\n" +
          "    },\n" +
          "    \"page\": {\n" +
          "        \"size\": 5,\n" +
          "        \"totalElements\": 9,\n" +
          "        \"totalPages\": 2,\n" +
          "        \"number\": 0\n" +
          "    }\n" +
          "}";

  public static final String ALL_SUBPRODUCTS =
      "{\n" +
          "    \"_embedded\": {\n" +
          "        \"products\": [\n" +
          "            {\n" +
          "                \"_type\": \"product\",\n" +
          "                \"id\": \"a54e56fb-2cc9-4eb7-b952-c16802e8debc\",\n" +
          "                \"name\": \"Child 1\",\n" +
          "                \"properties\": {\n" +
          "                    \"prop1\": \"some value\",\n" +
          "                    \"prop2\": 12.3,\n" +
          "                    \"prop3\": 1300399023,\n" +
          "                    \"prop4\": false,\n" +
          "                    \"prop5\": [\n" +
          "                        \"one\",\n" +
          "                        \"two\",\n" +
          "                        \"three\"\n" +
          "                    ]\n" +
          "                },\n" +
          "                \"created_at\": \"2021-03-14T23:02:43+02:00\",\n" +
          "                \"modified_at\": \"2021-03-14T23:02:43+02:00\",\n" +
          "                \"_links\": {\n" +
          "                    \"self\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/a54e56fb-2cc9-4eb7-b952-c16802e8debc\"\n"
          +
          "                    },\n" +
          "                    \"parent\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"root\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    }\n" +
          "                }\n" +
          "            },\n" +
          "            {\n" +
          "                \"_type\": \"product\",\n" +
          "                \"id\": \"afde9b10-e8f0-414d-8ccc-34734ffbeec4\",\n" +
          "                \"name\": \"Child 2\",\n" +
          "                \"properties\": {\n" +
          "                    \"prop1\": \"some value\",\n" +
          "                    \"prop2\": 12.3,\n" +
          "                    \"prop3\": 1300399023,\n" +
          "                    \"prop4\": false,\n" +
          "                    \"prop5\": [\n" +
          "                        \"one\",\n" +
          "                        \"two\",\n" +
          "                        \"three\"\n" +
          "                    ]\n" +
          "                },\n" +
          "                \"created_at\": \"2021-03-14T23:02:47+02:00\",\n" +
          "                \"modified_at\": \"2021-03-14T23:02:47+02:00\",\n" +
          "                \"_links\": {\n" +
          "                    \"self\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/afde9b10-e8f0-414d-8ccc-34734ffbeec4\"\n"
          +
          "                    },\n" +
          "                    \"parent\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"root\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    }\n" +
          "                }\n" +
          "            },\n" +
          "            {\n" +
          "                \"_type\": \"product\",\n" +
          "                \"id\": \"7598c4dc-b08a-4896-a59e-29521a2b544b\",\n" +
          "                \"name\": \"Child 3\",\n" +
          "                \"properties\": {\n" +
          "                    \"prop1\": \"some value\",\n" +
          "                    \"prop2\": 12.3,\n" +
          "                    \"prop3\": 1300399023,\n" +
          "                    \"prop4\": false,\n" +
          "                    \"prop5\": [\n" +
          "                        \"one\",\n" +
          "                        \"two\",\n" +
          "                        \"three\"\n" +
          "                    ]\n" +
          "                },\n" +
          "                \"created_at\": \"2021-03-14T23:02:49+02:00\",\n" +
          "                \"modified_at\": \"2021-03-14T23:02:49+02:00\",\n" +
          "                \"_links\": {\n" +
          "                    \"self\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/7598c4dc-b08a-4896-a59e-29521a2b544b\"\n"
          +
          "                    },\n" +
          "                    \"parent\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"root\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4\"\n"
          +
          "                    },\n" +
          "                    \"subProducts\": {\n" +
          "                        \"href\": \"http://localhost:8080/api/v1/products/7598c4dc-b08a-4896-a59e-29521a2b544b/subproducts?page=0&size=10\"\n"
          +
          "                    }\n" +
          "                }\n" +
          "            }\n" +
          "        ]\n" +
          "    },\n" +
          "    \"_links\": {\n" +
          "        \"self\": {\n" +
          "            \"href\": \"http://localhost:8080/api/v1/products/be8835b9-62ae-406f-be54-cd68f08dd1a4/subproducts?page=0&size=10\"\n"
          +
          "        }\n" +
          "    },\n" +
          "    \"page\": {\n" +
          "        \"size\": 10,\n" +
          "        \"totalElements\": 3,\n" +
          "        \"totalPages\": 1,\n" +
          "        \"number\": 0\n" +
          "    }\n" +
          "}";
}
