@from:cucumber
@ghActions:run_on_executor6
Feature: Export PP_Order to LeichMehl config

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-03T13:30:13+01:00[Europe/Berlin]

  @Id:S0162
  Scenario: Export PP_Order to LeichMehl when config is matched then a JsonExternalSystemRequest is sent to RabbitMQ
    Given load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And metasfresh contains M_Products:
      | Identifier                   | Name                         | OPT.M_Product_Category_ID.Identifier |
      | manufacturedProduct_27062022 | manufacturedProduct_27062022 | standard_category                    |
      | componentProduct_27062022    | componentProduct_27062022    |                                      |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier      | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | manufacturedProduct_27062022 | 2022-05-09 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier   | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | componentProduct_27062022 | 2022-05-09 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |
    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier      | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrder                | MOP         | manufacturedProduct_27062022 | 10         | testResource             | 2022-05-09T23:59:00.00Z | 2022-05-09T23:59:00.00Z | 2022-05-09T23:59:00.00Z | Y                |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue      | ExternalSystem_Config_LeichMehl_ID.Identifier | TCP_PortNumber | TCP_Host      | Product_BaseFolderName |
      | leichMehlConfig_27062022            | LM   | leichMehlConfig_27062022 | leichMehlConfig                               | 123            | doesNotMatter | ProductBaseFolderName  |

    And metasfresh contains LeichMehl_PluFile_ConfigGroup:
      | LeichMehl_PluFile_ConfigGroup_ID.Identifier | Name           |
      | leichMehlPLuFileConfigGroup_1               | PluConfigGroup |

    And metasfresh contains LeichMehl_PluFile_Config:
      | LeichMehl_PluFile_Config_ID.Identifier | LeichMehl_PluFile_ConfigGroup_ID.Identifier | TargetFieldName | TargetFieldType | Replacement           | ReplaceRegExp | ReplacementSource |
      | leichMehlPLuFileConfig_1               | leichMehlPLuFileConfigGroup_1               | TestField-01    | numberField     | @JsonPath=/productNo@ | .*            | Product           |

    And metasfresh contains ExternalSystem_Config_LeichMehl_ProductMapping:
      | ExternalSystem_Config_LeichMehl_ProductMapping_ID.Identifier | LeichMehl_PluFile_ConfigGroup_ID.Identifier | PLU_File    | M_Product_ID.Identifier      |
      | leichMehlProductMapping_1                                    | leichMehlPLuFileConfigGroup_1               | pluFilename | manufacturedProduct_27062022 |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    When export PP_Order to LeichMehl external system
      | PP_Order_ID.Identifier | ExternalSystem_Config_LeichMehl_ID.Identifier |
      | ppOrder                | leichMehlConfig                               |

    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.PP_Order_ID.Identifier | TCP_PortNumber | TCP_Host      | Product_BaseFolderName | IsPluFileExportAuditEnabled | ConfigMappings.pluFile | ConfigMappings.M_Product_ID.Identifier | PluFileConfig                                                                                                                                                                    |
      | leichMehlConfig_27062022            | ppOrder                    | 123            | doesNotMatter | ProductBaseFolderName  | false                       | pluFilename            | manufacturedProduct_27062022           | {"pluFileConfigs":[{"targetFieldName":"TestField-01","targetFieldType":"NumberField","replacePattern":".*","replacement":"@JsonPath=/productNo@","replacementSource":"Product"}]} |

