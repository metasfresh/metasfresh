@from:cucumber
Feature: import order candidate to metasfresh

  Background:
    Given infrastructure and metasfresh are running
	And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-10-10T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load IMP_Processor_Type
      | IMP_Processor_Type_ID.Identifier | Value    |
      | impProcessorType                 | RabbitMQ |

    And metasfresh contains IMP_Processor
      | IMP_Processor_ID.Identifier | Name               | IMP_Processor_Type_ID.Identifier | FrequencyType | Frequency |
      | impProcessor                | ediImportProcessor | impProcessorType                 | M             | 1         |

    And update IMP_ProcessorParameter for the following IMP_Processor:impProcessor
      | IMP_ProcessorParameter_ID.Identifier | Value          | ParameterValue |
      | param_1                              | exchangeName   | ediImport      |
      | param_2                              | queueName      | ediImport      |
      | param_3                              | isDurableQueue | true           |

    And after not more than 60s, import processor has started: impProcessor

    And metasfresh contains M_Products:
      | Identifier | Name         |
      | product    | salesProduct |
    And metasfresh contains M_PricingSystems
      | Identifier | Name              | Value              | OPT.IsActive |
      | ps_1       | PricingSystemName | PricingSystemValue | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | PriceListName | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV | 2022-10-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product                 | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name          |
      | huPackingTU_1         | huPackingTU_1 |
      | huPackingTU_2         | huPackingTU_2 |
      | huPackingTU_3         | huPackingTU_3 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name               | HU_UnitType | IsCurrent |
      | packingVersionTU_1            | huPackingTU_1         | packingVersionTU_1 | TU          | Y         |
      | packingVersionTU_2            | huPackingTU_2         | packingVersionTU_2 | TU          | Y         |
      | packingVersionTU_3            | huPackingTU_3         | packingVersionTU_3 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU_1               | packingVersionTU_1            | 10  | MI       |
      | huPiItemTU_2               | packingVersionTU_2            | 5   | MI       |
      | huPiItemTU_3               | packingVersionTU_3            | 7   | MI       |


  @from:cucumber
  Scenario: Properly identify M_HU_PI_Item_Product when metasfresh contains two different BPartners that share their main location but have different store locations; UPC is set on C_BPartner_Product
  _Given one main C_BPartner and one Subsidiary_BPartner that share their main location but their store locations are different
  _And 2 x M_HU_PI_Item_Product for each C_BPartner and for the same product
  _And UPC is set on C_BPartner_Product for both partners
  _When importing OLCand via EDIImportXML with: M_HU_PI_Item_Product(storeGLN is sent)
  _Then validate that C_OLCand was created
  _And M_HU_PI_Item_Product_ID = `101`- default M_HU_PI_Item_Product_ID when there is no UPC set on PIIP record

    Given metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner1_1 | MainBPartner_1       | N            | Y              | ps_1                          |
      | bpartner2_1 | SubsidiaryBPartner_1 | N            | Y              | ps_1                          |
      | bpartner3_1 | OrgBPartner_1        | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.UPC       | OPT.IsCurrentVendor |
      | bpartner1_1              | product                 | 1111111111111 | false               |
      | bpartner2_1              | product                 | 1111111111111 | false               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.Name             | OPT.C_UOM_ID.X12DE355 | OPT.C_BPartner_ID.Identifier |
      | huItemProduct_1_1                  | huPiItemTU_1               | product                 | 10  | 2022-10-01 | IFCO_Test_1 x 10 PCE | PCE                   | bpartner1_1                  |
      | huItemProduct_2_1                  | huPiItemTU_2               | product                 | 5   | 2022-10-01 | IFCO_Test_1 x 5 PCE  | PCE                   | bpartner2_1                  |

    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo |
      | bpLocation_main1_1  | 1234567890000 | bpartner1_1              | false        |
      | bpLocation_store1_1 | 1234567890001 | bpartner1_1              | true         |
      | bpLocation_main2_1  | 1234567890000 | bpartner2_1              | false        |
      | bpLocation_store2_1 | 1234567890002 | bpartner2_1              | true         |
      | bpLocation_org_1    | 2222222222220 | bpartner3_1              | false        |

    When send message to RabbitMQ queue defined by:impProcessor
  """
<?xml version="1.0" encoding="UTF-8"?><EDI_Imp_C_OLCand AD_Client_Value="SYSTEM" ReplicationEvent="5" ReplicationMode="0" ReplicationType="M" TrxName="" Version="*">
      <AD_DataDestination_ID>
        <InternalName>DEST.de.metas.ordercandidate</InternalName>
      </AD_DataDestination_ID>
      <AD_InputDataSource_ID>540217</AD_InputDataSource_ID>
      <AD_Org_ID>
      <GLN>2222222222220</GLN>
      </AD_Org_ID>
      <AD_User_EnteredBy_ID>2188223</AD_User_EnteredBy_ID>
      <C_BPartner_ID>
        <GLN>1234567890000</GLN>
        <StoreGLN>1234567890001</StoreGLN>
      </C_BPartner_ID>
      <C_BPartner_Location_ID>
        <C_BPartner_ID>
          <GLN>1234567890000</GLN>
          <StoreGLN>1234567890001</StoreGLN>
        </C_BPartner_ID>
        <GLN>1234567890000</GLN>
      </C_BPartner_Location_ID>
      <C_Currency_ID>
        <ISO_Code>EUR</ISO_Code>
      </C_Currency_ID>
      <C_UOM_ID>
        <X12DE355>KGM</X12DE355>
      </C_UOM_ID>
      <DateCandidate>2022-10-17+03:00</DateCandidate>
      <DeliveryRule>F</DeliveryRule>
      <DeliveryViaRule>S</DeliveryViaRule>
      <IsManualPrice>Y</IsManualPrice>
      <M_Product_ID>
        <UPC>1111111111111</UPC>
        <GLN>1234567890000</GLN>
      </M_Product_ID>
      <M_HU_PI_Item_Product_ID>
        <UPC>1111111111111</UPC>
        <GLN>1234567890000</GLN>
        <StoreGLN>1234567890001</StoreGLN>
      </M_HU_PI_Item_Product_ID>
      <PriceEntered>5</PriceEntered>
      <QtyEntered>10</QtyEntered>
      <POReference>PORefTest</POReference>
      <DatePromised>2022-11-20T23:59:59+03:00</DatePromised>
    </EDI_Imp_C_OLCand>
"""

    Then after not more than 120s, C_OLCand is found
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.C_BPartner_ID.Identifier | QtyEntered | OPT.IMP_Processor_ID.Identifier |
      | olCand_1               | product                 | 101                                    | bpartner1_1                  | 10         | impProcessor                    |

    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference | IsError | OPT.Processed | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | olCand_1               | bpartner1_1              | bpLocation_main1_1                | product                 | 10         | F            | S               | PORefTest       | Y       | N             | 101                                    |

  @from:cucumber
  Scenario: Properly identify M_HU_PI_Item_Product when metasfresh contains three different BPartners:first partner shares the same main location with the second one and same store location with the third one.
  _Given three BPartners, each of them having two locations: main and store
  _And MainBPartner shares its main location with BPartner2
  _And MainBPartner shares its store location with BPartner3
  _And 3 x M_HU_PI_Item_Product for each C_BPartner, having the same product and UPC
  _When importing OLCand via EDIImportXML that also contains: M_HU_PI_Item_Product(storeGLN is sent), C_BPartner, C_BPartner_Location, DropShip_BPartner, DropShip_Location, HandOverBPartner, HandOver_Location
  _Then validate that C_OLCand was created and all specified fields were properly resolved
  _And M_HU_PI_Item_Product_ID was chosen based on `storeGLN`
  _When importing OLCand via EDIImportXML that also contains: M_HU_PI_Item_Product(storeGLN is not sent), C_BPartner, C_BPartner_Location, DropShip_BPartner, DropShip_Location, HandOverBPartner, HandOver_Location
  _Then validate that C_OLCand was created and all specified fields were properly resolved
  _And C_OLCand.M_HU_PI_Item_Product_ID is set to the most recently created PIIP for the given GLN

    Given metasfresh contains C_BPartners:
      | Identifier    | Name           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner1_2   | MainBPartner_2 | N            | Y              | ps_1                          |
      | bpartner2_2   | BPartner2_2    | N            | Y              | ps_1                          |
      | bpartner3_2   | BPartner3_2    | N            | Y              | ps_1                          |
      | orgBPartner_2 | OrgBPartner_2  | N            | Y              | ps_1                          |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.Name            | OPT.C_UOM_ID.X12DE355 | OPT.C_BPartner_ID.Identifier | OPT.UPC       |
      | huItemProduct_1_2                  | huPiItemTU_1               | product                 | 2   | 2022-10-01 | IFCO_Test_1 x 2 PCE | PCE                   | bpartner1_2                  | 1111111111112 |
      | huItemProduct_2_2                  | huPiItemTU_2               | product                 | 9   | 2022-10-01 | IFCO_Test_1 x 9 PCE | PCE                   | bpartner2_2                  | 1111111111112 |
      | huItemProduct_3_2                  | huPiItemTU_3               | product                 | 7   | 2022-10-01 | IFCO_Test_1 x 7 PCE | PCE                   | bpartner3_2                  | 1111111111112 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo |
      | mainBPLocation1_2  | 2234567890000 | bpartner1_2              | false        |
      | storeBPLocation1_2 | 2234567890001 | bpartner1_2              | true         |
      | mainBPLocation2_2  | 2234567890000 | bpartner2_2              | false        |
      | storeBPLocation2_2 | 2234567890002 | bpartner2_2              | true         |
      | mainBPLocation3_2  | 2234567890003 | bpartner3_2              | false        |
      | storeBPLocation3_2 | 2234567890001 | bpartner3_2              | true         |
      | orgBPLocation_2    | 2222222222221 | orgBPartner_2            | false        |

    When send message to RabbitMQ queue defined by:impProcessor
  """
<?xml version="1.0" encoding="UTF-8"?><EDI_Imp_C_OLCand AD_Client_Value="SYSTEM" ReplicationEvent="5" ReplicationMode="0" ReplicationType="M" TrxName="" Version="*">
    <AD_DataDestination_ID>
        <InternalName>DEST.de.metas.ordercandidate</InternalName>
    </AD_DataDestination_ID>
    <AD_InputDataSource_ID>540217</AD_InputDataSource_ID>
    <AD_Org_ID>
        <GLN>2222222222221</GLN>
    </AD_Org_ID>
    <AD_User_EnteredBy_ID>2188223</AD_User_EnteredBy_ID>
    <C_BPartner_ID>
        <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
    </C_BPartner_ID>
    <DropShip_BPartner_ID>
        <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
    </DropShip_BPartner_ID>
    <HandOver_Partner_ID>
        <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
    </HandOver_Partner_ID>
    <C_BPartner_Location_ID>
        <C_BPartner_ID>
            <GLN>2234567890000</GLN>
            <StoreGLN>2234567890001</StoreGLN>
        </C_BPartner_ID>
        <GLN>2234567890000</GLN>
    </C_BPartner_Location_ID>
    <DropShip_Location_ID>
        <C_BPartner_ID>
            <GLN>2234567890000</GLN>
            <StoreGLN>2234567890001</StoreGLN>
        </C_BPartner_ID>
        <GLN>2234567890001</GLN>
    </DropShip_Location_ID>
    <HandOver_Location_ID>
        <C_BPartner_ID>
            <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
        </C_BPartner_ID>
        <GLN>2234567890000</GLN>
    </HandOver_Location_ID>
    <C_Currency_ID>
        <ISO_Code>EUR</ISO_Code>
    </C_Currency_ID>
    <C_UOM_ID>
        <X12DE355>KGM</X12DE355>
    </C_UOM_ID>
    <DateCandidate>2022-10-17+03:00</DateCandidate>
    <DeliveryRule>F</DeliveryRule>
    <DeliveryViaRule>S</DeliveryViaRule>
    <IsManualPrice>Y</IsManualPrice>
    <M_Product_ID>
        <UPC>1111111111112</UPC>
        <GLN>2234567890000</GLN>
    </M_Product_ID>
    <M_HU_PI_Item_Product_ID>
        <UPC>1111111111112</UPC>
        <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
    </M_HU_PI_Item_Product_ID>
    <PriceEntered>11</PriceEntered>
    <QtyEntered>2</QtyEntered>
    <POReference>PORefTest</POReference>
    <DatePromised>2022-11-20T23:59:59+03:00</DatePromised>
</EDI_Imp_C_OLCand>
"""

    Then after not more than 120s, C_OLCand is found
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.C_BPartner_ID.Identifier | QtyEntered | OPT.IMP_Processor_ID.Identifier |
      | olCand_2               | product                 | huItemProduct_1_2                      | bpartner1_2                  | 2          | impProcessor                    |

    And validate C_OLCand:
      | C_OLCand_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.DropShip_BPartner_ID.Identifier | OPT.DropShip_Location_ID.Identifier | OPT.HandOver_Partner_ID.Identifier | OPT.HandOver_Location_ID.Identifier | M_Product_ID.Identifier | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference | IsError | OPT.Processed |
      | olCand_2               | huItemProduct_1_2                      | bpartner1_2              | mainBPLocation1_2                 | bpartner1_2                         | storeBPLocation1_2                  | bpartner1_2                        | mainBPLocation1_2                   | product                 | 2          | F            | S               | PORefTest       | Y       | N             |

    When send message to RabbitMQ queue defined by:impProcessor
  """
<?xml version="1.0" encoding="UTF-8"?><EDI_Imp_C_OLCand AD_Client_Value="SYSTEM" ReplicationEvent="5" ReplicationMode="0" ReplicationType="M" TrxName="" Version="*">
    <AD_DataDestination_ID>
        <InternalName>DEST.de.metas.ordercandidate</InternalName>
    </AD_DataDestination_ID>
    <AD_InputDataSource_ID>540217</AD_InputDataSource_ID>
    <AD_Org_ID>
        <GLN>2222222222221</GLN>
    </AD_Org_ID>
    <AD_User_EnteredBy_ID>2188223</AD_User_EnteredBy_ID>
    <C_BPartner_ID>
        <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
    </C_BPartner_ID>
    <DropShip_BPartner_ID>
        <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
    </DropShip_BPartner_ID>
    <HandOver_Partner_ID>
        <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
    </HandOver_Partner_ID>
    <C_BPartner_Location_ID>
        <C_BPartner_ID>
            <GLN>2234567890000</GLN>
            <StoreGLN>2234567890001</StoreGLN>
        </C_BPartner_ID>
        <GLN>2234567890000</GLN>
    </C_BPartner_Location_ID>
    <DropShip_Location_ID>
        <C_BPartner_ID>
            <GLN>2234567890000</GLN>
            <StoreGLN>2234567890001</StoreGLN>
        </C_BPartner_ID>
        <GLN>2234567890001</GLN>
    </DropShip_Location_ID>
    <HandOver_Location_ID>
        <C_BPartner_ID>
            <GLN>2234567890000</GLN>
        <StoreGLN>2234567890001</StoreGLN>
        </C_BPartner_ID>
        <GLN>2234567890000</GLN>
    </HandOver_Location_ID>
    <C_Currency_ID>
        <ISO_Code>EUR</ISO_Code>
    </C_Currency_ID>
    <C_UOM_ID>
        <X12DE355>KGM</X12DE355>
    </C_UOM_ID>
    <DateCandidate>2022-10-17+03:00</DateCandidate>
    <DeliveryRule>F</DeliveryRule>
    <DeliveryViaRule>S</DeliveryViaRule>
    <IsManualPrice>Y</IsManualPrice>
    <M_Product_ID>
        <UPC>1111111111112</UPC>
        <GLN>2234567890000</GLN>
    </M_Product_ID>
    <M_HU_PI_Item_Product_ID>
        <UPC>1111111111112</UPC>
        <GLN>2234567890000</GLN>
    </M_HU_PI_Item_Product_ID>
    <PriceEntered>11</PriceEntered>
    <QtyEntered>2</QtyEntered>
    <POReference>PORefTest</POReference>
    <DatePromised>2022-11-20T23:59:59+03:00</DatePromised>
</EDI_Imp_C_OLCand>
"""

    Then after not more than 120s, C_OLCand is found
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.C_BPartner_ID.Identifier | QtyEntered | OPT.IMP_Processor_ID.Identifier |
      | olCand_3               | product                 | huItemProduct_2_2                      | bpartner1_2                  | 2          | impProcessor                    |

    And validate C_OLCand:
      | C_OLCand_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.DropShip_BPartner_ID.Identifier | OPT.DropShip_Location_ID.Identifier | OPT.HandOver_Partner_ID.Identifier | OPT.HandOver_Location_ID.Identifier | M_Product_ID.Identifier | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference | IsError | OPT.Processed |
      | olCand_3               | huItemProduct_2_2                      | bpartner1_2              | mainBPLocation1_2                 | bpartner1_2                         | storeBPLocation1_2                  | bpartner1_2                        | mainBPLocation1_2                   | product                 | 2          | F            | S               | PORefTest       | Y       | N             |
