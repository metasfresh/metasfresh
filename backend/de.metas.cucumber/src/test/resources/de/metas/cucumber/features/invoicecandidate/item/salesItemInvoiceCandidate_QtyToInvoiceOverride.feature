@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@F00701
@ghActions:run_on_executor6
Feature: Make sure C_Invoice_Candidate.QtyToInvoice_Override is respected
## F00701: Invoice Candidates

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    When load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    When metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_1                  | p_1          | 10.0     | PCE      |
    And load C_Aggregations:
      | Identifier               | C_Aggregation_ID |
      | one-invoice-per-shipment | 540017           |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID | SO_Invoice_Aggregation_ID |
      | endcustomer_1 | N        | Y          | ps_1               | one-invoice-per-shipment  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | l_1        | endcustomer_1 | Y               | Y               |


    
    
## ####################################################################################################################################################################
## ####################################################################################################################################################################
## ####################################################################################################################################################################
## ####################################################################################################################################################################
## ####################################################################################################################################################################
## ####################################################################################################################################################################

  @Id:03082022-SIC.160
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: Order 100, Ship 70, Force Invoice 90 (one-invoice-per-shipment aggregation)
    #
    # Order 100
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | o_1        | true    | endcustomer_1 | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 100        |
    And the order identified by o_1 is completed

    #
    # Ship 70
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | s_ol_1     | ol_1           | N             |
    When 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_ol_1                           | D            | true                | false       | 70                                              |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | s_ol_1                | shipment_1 |
    And validate the created shipments
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | movementqty |
      | shipmentLine_1 | shipment_1 | p_1          | 70          |
    
    #
    # Check the invoice candidate
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_1                   | ol_1           |
    And validate created C_InvoiceCandidate_InOutLine
      | C_Invoice_Candidate_ID | M_InOutLine_ID | QtyDelivered |
      | ic_1                   | shipmentLine_1 | 70           |
    
    #
    # Invoice 90
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyDelivered |
      | ic_1                              | 70           |
    And update invoice candidates
      | C_Invoice_Candidate_ID | QtyToInvoice_Override |
      | ic_1                   | 90                    |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice |
      | ic_1                              | 90           |
    And process invoice candidates
      | C_Invoice_Candidate_ID |
      | ic_1                   |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic_1                   | invoice      |
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | Processed |
      | invoice      | p_1          | 90          | true      |
