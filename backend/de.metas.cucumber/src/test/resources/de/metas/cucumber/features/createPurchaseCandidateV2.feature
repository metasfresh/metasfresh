@from:cucumber
Feature: create or update Purchase Candidate
  As a user
  I want create a Purchase Candidate record

  Background:
    Given infrastructure and metasfresh are running
	And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Name                | Value               |
      | poActivity_31052022      | poActivity_31052022 | poActivity_31052022 |

    And the user adds a purchase candidate
      | ExternalLineId | ExternalHeaderId | POReference | orgCode | warehouse        | isManualPrice | isManualDiscount | product     | vendor.id | qty | qty.uom | OPT.ExternalPurchaseOrderURL | OPT.ProductDescription      | OPT.C_Activity_ID.Identifier |
      | L1             | H1               | poRef1      | 001     | val-StdWarehouse | false         | false            | val-P002737 | val-G0002 | 3   | PCE     | www.ExternalReferenceURL.com | productDescription_31052022 | poActivity_31052022          |

    And the user adds a purchase candidate price
      | value | OPT.currencyCode | OPT.priceUomCode |
      | 1.5   | EUR              | PCE              |

    And the user adds a purchase candidate enqueue request
      | ExternalHeaderId | ExternalLineId |
      | H1               | L1             |

  @from:cucumber
  Scenario:  The purchase candidate request is set in context, enqueued and validated
    And the purchase candidate request is set in context
    When the metasfresh REST-API endpoint path 'api/v2/order/purchase/createCandidates' receives a 'POST' request with the payload from context and responds with '200' status code
    Then verify if data is persisted correctly for purchase candidate

    Given the purchase candidate enqueue-status request is set in context
    Then the metasfresh REST-API endpoint path 'api/v2/order/purchase/enqueueForOrdering' receives a 'POST' request with the payload from context and responds with '202' status code
    And a PurchaseOrder with externalId 'H1' is created after not more than 30 seconds and has values
      | OPT.C_Order_ID.Identifier | ExternalPurchaseOrderURL     | POReference |
      | po_1                      | www.ExternalReferenceURL.com | poRef1      |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.ProductDescription      | OPT.C_Activity_ID.Identifier |
      | po_line_1                 | po_1                  | 2005577                 | 0            | 3          | 0           | 1     | 0        | EUR          | true      | productDescription_31052022 | poActivity_31052022          |

    Given the purchase candidate enqueue-status request is set in context
    When the metasfresh REST-API endpoint path 'api/v2/order/purchase/status' receives a 'PUT' request with the payload from context and responds with '200' status code
    Then verify purchase candidate status
