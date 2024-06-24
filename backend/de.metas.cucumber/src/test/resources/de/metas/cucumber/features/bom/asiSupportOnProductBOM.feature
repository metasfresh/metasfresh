@from:cucumber
@ghActions:run_on_executor3
Feature: ASI support in Product BOM rest-api
  Add ProductBOM and ProductBOMLine with ASI via rest-api
  Using default ad_orgId 1000000

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-02T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_AttributeSet:
      | M_AttributeSet_ID.Identifier   | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_convenienceSalate   |

    And metasfresh contains M_PricingSystems
      | Identifier | Name    | Value   | OPT.IsActive |
      | ps_SO_1    | ps_SO_1 | ps_SO_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_SO      | ps_SO_1                       | DE                        | EUR                 | pl_SO_name | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_SO     | pl_SO                     | plv_SO | 2021-01-01 |

    And metasfresh contains C_BPartners:
      | Identifier  | Name        | Value       | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_SO | customer_SO | customer_SO | Y              | ps_SO_1                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN          | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | customerLocation_SO | customerSO01 | customer_SO              | Y                   | Y                   |
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 0        |

  @from:cucumber
  Scenario: Create sales order with different ASI, on complete no production candidate is found
    Given metasfresh contains M_Products:
      | Identifier   | Value             | Name              | OPT.M_Product_Category_ID.Identifier |
      | product_S1   | product_S1        | product_S1        | standard_category                    |
      | component_S1 | componentValue_S1 | componentValue_S1 | standard_category                    |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_S1              | 10.0     | PCE               | Normal                        |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference       | ExternalReferenceType.Code | RecordId.Identifier |
      | GRSSignum           | productExternalRef_S1   | Product                    | product_S1          |
      | GRSSignum           | componentExternalRef_S1 | Product                    | component_S1        |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/material/bom/version/001' and fulfills with '200' status code

    """
{
   "uomCode":"PCE",
   "productIdentifier":"ext-GRSSignum-productExternalRef_S1",
   "name":"product_S1",
   "isActive":true,
   "validFrom":"2022-01-03T11:09:47.000000Z",
   "bomLines":[
      {
         "productIdentifier":"ext-GRSSignum-componentExternalRef_S1",
         "qtyBom":{
            "qty":5.0,
            "uomCode":"PCE"
         },
         "isQtyPercentage": false,
         "line": 1,
         "attributeSetInstance":{
            "attributeInstances":[
               {
                  "attributeCode":"1000002",
                  "valueStr":"Bio"
               }
            ]
         }
      }
   ],
   "attributeSetInstance":{
      "attributeInstances":[
         {
            "attributeCode":"1000002",
            "valueStr":"Bio"
         },
         {
            "attributeCode":"1000015",
            "valueStr":"GMAA"
         }
      ]
   }
}
    """

    Then process metasfresh response:
      | PP_Product_BOM_ID.Identifier | PP_Product_BOMVersions_ID.Identifier |
      | bom_1                        | bv_1                                 |

    And verify that bomVersions was created for product
      | PP_Product_BOMVersions_ID.Identifier | M_Product_ID.Identifier | Name       |
      | bv_1                                 | product_S1              | product_S1 |
    And verify that bom was created for product
      | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | ProductValue | UomCode | ValidFrom                   | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1                        | product_S1              | bv_1                                 | product_S1   | PCE     | 2022-01-03T11:09:47.000000Z | bomAttributeSetInstance                  |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID.Identifier | Value    |
      | bomAttributeSetInstance              | Bio,GMAA |

    And verify that bomLine was created for bom
      | OPT.PP_Product_BOMLine_ID.Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | Qty | UomCode | IsQtyPercentage | Scrap | Line | OPT.M_AttributeSetInstance_ID.Identifier |
      | bomLine_1                            | bom_1                        | component_S1            | 5.0 | PCE     | false           | 0     | 1    | bomLineAttributeSetInstance              |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID.Identifier | Value |
      | bomLineAttributeSetInstance          | Bio   |

    And verify BOM for M_Product:
      | M_Product_ID.Identifier |
      | product_S1              |

    And metasfresh contains M_AttributeSetInstance with identifier "ppProductPlanningAttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier | IsAttributeDependant |
      | pp_finishedGood | product_S1              | bv_1                                     | false        | ppProductPlanningAttributeSetInstance    | true                 |

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineAttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier |
      | order_SO   | Y       | customer_SO              | 2022-01-03  | ps_SO_1                           |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine_SO | order_SO              | product_S1              | 5          | orderLineAttributeSetInstance            |

    And metasfresh has no AD_EventLog_Entry records

    And the order identified by order_SO is completed

      And after not more than 60s, AD_EventLog are found
        | AD_EventLog_ID.Identifier | EventType           | SupplyRequiredEvent.M_Product_ID.Identifier | EventData.Pattern   |
        | eventLog_1                | SupplyRequiredEvent | product_S1                                  | SupplyRequiredEvent |

      And after not more than 60s, AD_EventLog_Entry are found
        | AD_EventLog_Entry_ID.Identifier | AD_EventLog_ID.Identifier | Classname                                              | MsgText                                               | Processed |
        | eventLogEntry_1                 | eventLog_1                | de.metas.material.planning.event.SupplyRequiredHandler | No PP_Product_Planning record found => nothing to do; | false     |
        | eventLogEntry_2                 | eventLog_1                | de.metas.material.planning.event.SupplyRequiredHandler | this handler is done                                  | true      |

  @from:cucumber
  Scenario: Create sales order without ASI, on complete production candidate is found having the productPlanning ASI
    Given metasfresh contains M_Products:
      | Identifier   | Value        | Name         | OPT.M_Product_Category_ID.Identifier |
      | product_S2   | product_S2   | product_S2   | standard_category                    |
      | component_S2 | component_S2 | component_S2 | standard_category                    |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_S2              | 10.0     | PCE               | Normal                        |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference       | ExternalReferenceType.Code | RecordId.Identifier |
      | GRSSignum           | productExternalRef_S2   | Product                    | product_S2          |
      | GRSSignum           | componentExternalRef_S2 | Product                    | component_S2        |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/material/bom/version/001' and fulfills with '200' status code

    """
{
   "uomCode":"PCE",
   "productIdentifier":"ext-GRSSignum-productExternalRef_S2",
   "name":"product_S2",
   "isActive":true,
   "validFrom":"2022-01-03T11:09:47.000000Z",
   "bomLines":[
      {
         "productIdentifier":"ext-GRSSignum-componentExternalRef_S2",
         "qtyBom":{
            "qty":5.0,
            "uomCode":"PCE"
         },
         "isQtyPercentage": false,
         "line": 1,
         "attributeSetInstance":{
            "attributeInstances":[
               {
                  "attributeCode":"1000002",
                  "valueStr":"Bio"
               }
            ]
         }
      }
   ],
   "attributeSetInstance":{
      "attributeInstances":[
         {
            "attributeCode":"1000002",
            "valueStr":"Bio"
         },
         {
            "attributeCode":"1000015",
            "valueStr":"GMAA"
         }
      ]
   }
}
    """

    Then process metasfresh response:
      | PP_Product_BOM_ID.Identifier | PP_Product_BOMVersions_ID.Identifier |
      | bom_1                        | bv_1                                 |

    And verify that bomVersions was created for product
      | PP_Product_BOMVersions_ID.Identifier | M_Product_ID.Identifier | Name       |
      | bv_1                                 | product_S2              | product_S2 |

    And verify that bom was created for product
      | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | ProductValue | UomCode | ValidFrom                   | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1                        | product_S2              | bv_1                                 | product_S2   | PCE     | 2022-01-03T11:09:47.000000Z | bomAttributeSetInstance                  |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID.Identifier | Value    |
      | bomAttributeSetInstance              | Bio,GMAA |

    And verify that bomLine was created for bom
      | OPT.PP_Product_BOMLine_ID.Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | Qty | UomCode | IsQtyPercentage | Scrap | Line | OPT.M_AttributeSetInstance_ID.Identifier |
      | bomLine_1                            | bom_1                        | component_S2            | 5.0 | PCE     | false           | 0     | 1    | bomLineAttributeSetInstance              |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID.Identifier | Value |
      | bomLineAttributeSetInstance          | Bio   |

    And verify BOM for M_Product:
      | M_Product_ID.Identifier |
      | product_S2              |

    And metasfresh contains M_AttributeSetInstance with identifier "ppProductPlanningAttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
        "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier | IsAttributeDependant |
      | pp_finishedGood | product_S2              | bv_1                                     | false        | ppProductPlanningAttributeSetInstance    | true                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier | OPT.PreparationDate  |
      | order_SO   | Y       | customer_SO              | 2022-01-03  | ps_SO_1                           | 2022-01-08T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO | order_SO              | product_S2              | 5          |
    And the order identified by order_SO is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier      | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_finishedGood | false     | product_S2              | bom_1                        | pp_finishedGood                   | 540006        | 5          | 5            | 0            | PCE               | 2022-01-08T21:00:00Z | 2022-01-08T21:00:00Z | false    | bomAttributeSetInstance                  |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier       | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_finishedGood                  | olc_finishedGood | component_S2            | 25         | PCE               | CO            | bomLine_1                        | bomLineAttributeSetInstance              |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_111      | DEMAND            | SHIPMENT                      | product_S2              | 2022-01-08T21:00:00Z | -5  | -5                     |                                          |
      | c_222      | SUPPLY            | PRODUCTION                    | product_S2              | 2022-01-08T21:00:00Z | 5   | 5                      | bomAttributeSetInstance                  |

  @from:cucumber
  Scenario: Create sales order with the same ASI, on complete production candidate is found having the same ASI
    Given metasfresh contains M_Products:
      | Identifier   | Value        | Name         | OPT.M_Product_Category_ID.Identifier |
      | product_S3   | product_S3   | product_S3   | standard_category                    |
      | component_S3 | component_S3 | component_S3 | standard_category                    |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_S3              | 10.0     | PCE               | Normal                        |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference     | ExternalReferenceType.Code | RecordId.Identifier |
      | GRSSignum           | productExternalRef_S3 | Product                    | product_S3          |
      | GRSSignum           | componentExternalRef  | Product                    | component_S3        |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/material/bom/version/001' and fulfills with '200' status code

    """
{
   "uomCode":"PCE",
   "productIdentifier":"ext-GRSSignum-productExternalRef_S3",
   "name":"product_S3",
   "isActive":true,
   "validFrom":"2022-01-03T11:09:47.000000Z",
   "bomLines":[
      {
         "productIdentifier":"ext-GRSSignum-componentExternalRef",
         "qtyBom":{
            "qty":5.0,
            "uomCode":"PCE"
         },
         "isQtyPercentage": false,
         "line": 1,
         "attributeSetInstance":{
            "attributeInstances":[
               {
                  "attributeCode":"1000002",
                  "valueStr":"Bio"
               },
               {
                  "attributeCode":"1000015",
                  "valueStr":"GMAA"
               }
            ]
         }
      }
   ],
   "attributeSetInstance":{
      "attributeInstances":[
         {
            "attributeCode":"1000002",
            "valueStr":"Bio"
         }
      ]
   }
}
    """

    Then process metasfresh response:
      | PP_Product_BOM_ID.Identifier | PP_Product_BOMVersions_ID.Identifier |
      | bom_1                        | bv_1                                 |

    And verify that bomVersions was created for product
      | PP_Product_BOMVersions_ID.Identifier | M_Product_ID.Identifier | Name       |
      | bv_1                                 | product_S3              | product_S3 |

    And verify that bom was created for product
      | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | ProductValue | UomCode | ValidFrom                   | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1                        | product_S3              | bv_1                                 | product_S3   | PCE     | 2022-01-03T11:09:47.000000Z | bomAttributeSetInstance                  |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID.Identifier | Value |
      | bomAttributeSetInstance              | Bio   |

    And verify that bomLine was created for bom
      | OPT.PP_Product_BOMLine_ID.Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | Qty | UomCode | IsQtyPercentage | Scrap | Line | OPT.M_AttributeSetInstance_ID.Identifier |
      | bomLine_1                            | bom_1                        | component_S3            | 5.0 | PCE     | false           | 0     | 1    | bomLineAttributeSetInstance              |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID.Identifier | Value    |
      | bomLineAttributeSetInstance          | Bio,GMAA |

    And verify BOM for M_Product:
      | M_Product_ID.Identifier |
      | product_S3              |

    And metasfresh contains M_AttributeSetInstance with identifier "ppProductPlanningAttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier | IsAttributeDependant |
      | pp_finishedGood | product_S3              | bv_1                                     | false        | ppProductPlanningAttributeSetInstance    | true                 |

    And metasfresh contains M_PricingSystems
      | Identifier | Name  | Value | OPT.IsActive |
      | ps_PO      | ps_PO | ps_PO | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_PO_name | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_PO     | pl_PO                     | plv_PO | 2022-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | product_S3              | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name        | Value         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | supplier_PO | supplier_PO | supplier_PO_1 | Y            | N              | ps_PO                         | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN          | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | supplierLocation_PO | supplierP101 | supplier_PO              | Y                   | Y                   |

    And metasfresh contains M_AttributeSetInstance with identifier "po_AttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.DatePromised     |
      | order_PO   | N       | supplier_PO              | 2022-01-05  | po_ref          | 1000012              | POO             | ps_PO                             | 2022-01-08T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine_PO | order_PO              | product_S3              | 10         | po_AttributeSetInstance                  |
    And the order identified by order_PO is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | md_po      | SUPPLY            | PURCHASE                      | product_S3              | 2022-01-08T21:00:00Z | 10  | 10                     | po_AttributeSetInstance                  |

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineAttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier | OPT.DatePromised     |
      | order_SO   | Y       | customer_SO              | 2022-01-09  | ps_SO_1                           | 2022-01-08T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine_SO | order_SO              | product_S3              | 20         | orderLineAttributeSetInstance            |
    And the order identified by order_SO is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier      | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_finishedGood | false     | product_S3              | bom_1                        | pp_finishedGood                   | 540006        | 10         | 10           | 0            | PCE               | 2022-01-08T21:00:00Z | 2022-01-08T21:00:00Z | false    | bomAttributeSetInstance                  |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier       | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_finishedGood                  | olc_finishedGood | component_S3            | 50         | PCE               | CO            | bomLine_1                        | bomLineAttributeSetInstance              |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_111      | DEMAND            | SHIPMENT                      | product_S3              | 2022-01-08T21:00:00Z | -20 | -10                    | orderLineAttributeSetInstance            |
      | c_222      | SUPPLY            | PRODUCTION                    | product_S3              | 2022-01-08T21:00:00Z | 10  | 0                      | orderLineAttributeSetInstance            |

  @from:cucumber
  Scenario: Create sales order with ASI (country-M_Attribute 1000001), on complete production candidate is found having the same ASI
    Given metasfresh contains M_Products:
      | Identifier   | Value        | Name         | OPT.M_Product_Category_ID.Identifier |
      | product_S4   | product_S4   | product_S4   | standard_category                    |
      | component_S4 | component_S4 | component_S4 | standard_category                    |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | product_S4              | 10.0     | PCE               | Normal                        |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference       | ExternalReferenceType.Code | RecordId.Identifier |
      | GRSSignum           | productExternalRef_S4   | Product                    | product_S4          |
      | GRSSignum           | componentExternalRef_S4 | Product                    | component_S4        |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/material/bom/version/001' and fulfills with '200' status code

    """
{
   "uomCode":"PCE",
   "productIdentifier":"ext-GRSSignum-productExternalRef_S4",
   "name":"product_S4",
   "isActive":true,
   "validFrom":"2022-01-03T11:09:47.000000Z",
   "attributeSetInstance":{
      "attributeInstances":[
         {
            "attributeCode":"1000001",
            "valueStr":"DE"
         }
      ]
   },
   "bomLines":[
      {
         "productIdentifier":"ext-GRSSignum-componentExternalRef_S4",
         "qtyBom":{
            "qty":5.0,
            "uomCode":"PCE"
         },
         "isQtyPercentage": false,
         "line": 1,
         "attributeSetInstance":{
            "attributeInstances":[
               {
                  "attributeCode":"1000001",
                  "valueStr":"DE"
               },
               {
                  "attributeCode":"1000015",
                  "valueStr":"GMAA"
               }
            ]
         }
      }
   ]
}
    """

    Then process metasfresh response:
      | PP_Product_BOM_ID.Identifier | PP_Product_BOMVersions_ID.Identifier |
      | bom_1                        | bv_1                                 |

    And verify that bomVersions was created for product
      | PP_Product_BOMVersions_ID.Identifier | M_Product_ID.Identifier | Name       |
      | bv_1                                 | product_S4              | product_S4 |

    And verify that bom was created for product
      | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | ProductValue | UomCode | ValidFrom                   | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1                        | product_S4              | bv_1                                 | product_S4   | PCE     | 2022-01-03T11:09:47.000000Z | bomAttributeSetInstance                  |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID.Identifier | Value |
      | bomAttributeSetInstance              | DE    |

    And verify that bomLine was created for bom
      | OPT.PP_Product_BOMLine_ID.Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | Qty | UomCode | IsQtyPercentage | Scrap | Line | OPT.M_AttributeSetInstance_ID.Identifier |
      | bomLine_1                            | bom_1                        | component_S4            | 5.0 | PCE     | false           | 0     | 1    | bomLineAttributeSetInstance              |
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID.Identifier | Value   |
      | bomLineAttributeSetInstance          | DE,GMAA |

    And verify BOM for M_Product:
      | M_Product_ID.Identifier |
      | product_S4              |

    And metasfresh contains M_AttributeSetInstance with identifier "ppProductPlanningAttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000001",
        "valueStr":"DE"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier | IsAttributeDependant |
      | pp_finishedGood | product_S4              | bv_1                                     | false        | ppProductPlanningAttributeSetInstance    | true                 |

    And metasfresh contains M_PricingSystems
      | Identifier | Name  | Value | OPT.IsActive |
      | ps_PO      | ps_PO | ps_PO | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_PO_name | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_PO     | pl_PO                     | plv_PO | 2022-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | product_S4              | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name        | Value         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | supplier_PO | supplier_PO | supplier_PO_1 | Y            | N              | ps_PO                         | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN          | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | supplierLocation_PO | supplierP101 | supplier_PO              | true                | true                |

    And metasfresh contains M_AttributeSetInstance with identifier "po_AttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000001",
        "valueStr":"DE"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.DatePromised     |
      | order_PO   | N       | supplier_PO              | 2022-01-05  | po_ref          | 1000012              | POO             | ps_PO                             | 2022-01-08T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine_PO | order_PO              | product_S4              | 10         | po_AttributeSetInstance                  |
    And the order identified by order_PO is completed

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | po_supply  | SUPPLY            | PURCHASE                      | product_S4              | 2022-01-08T21:00:00Z | 10  | 10                     | po_AttributeSetInstance                  |

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineAttributeSetInstance":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000001",
        "valueStr":"DE"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier | OPT.DatePromised     |
      | order_SO   | Y       | customer_SO              | 2022-01-09  | ps_SO_1                           | 2022-01-08T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine_SO | order_SO              | product_S4              | 20         | orderLineAttributeSetInstance            |
    And the order identified by order_SO is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier      | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_finishedGood | false     | product_S4              | bom_1                        | pp_finishedGood                   | 540006        | 10         | 10           | 0            | PCE               | 2022-01-08T21:00:00Z | 2022-01-08T21:00:00Z | false    | bomAttributeSetInstance                  |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier       | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_finishedGood                  | olc_finishedGood | component_S4            | 50         | PCE               | CO            | bomLine_1                        | bomLineAttributeSetInstance              |

    And after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | po_supply   | SUPPLY            | PURCHASE                      | product_S4              | 2022-01-08T21:00:00Z | 10  | 10                     | po_AttributeSetInstance                  |
      | so_demand   | DEMAND            | SHIPMENT                      | product_S4              | 2022-01-08T21:00:00Z | -20 | -10                    | orderLineAttributeSetInstance            |
      | prod_supply | SUPPLY            | PRODUCTION                    | product_S4              | 2022-01-08T21:00:00Z | 10  | 0                      | bomAttributeSetInstance                  |