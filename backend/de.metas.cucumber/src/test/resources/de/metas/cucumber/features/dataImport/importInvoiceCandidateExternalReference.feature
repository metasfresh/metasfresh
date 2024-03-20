@Id:S0325
@from:cucumber
@ghActions:run_on_executor5
Feature: Import Invoice Candidates via DataImportRestController

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has current date and time
    And metasfresh initially has no I_Invoice_Candidate import data
    And load AD_ImpFormat:
      | AD_ImpFormat_ID.Identifier | Name                                        |
      | importFormat               | Import Invoice Candidate External Reference |
    And load AD_Org from AD_ImpFormat config:
      | AD_Org_ID.Identifier | AD_ImpFormat_ID.Identifier | OrgCode.ImportRowFieldName          |
      | importFormatOrg      | importFormat               | Organisation Suchschlüssel Standard |
    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.IsDefault |
      | importDocType           | ARI             | true          |
    And load C_UOM:
      | C_UOM_ID.Identifier | X12DE355 |
      | importUOM           | PCE      |
    And load product by value:
      | M_Product_ID.Identifier | Value   |
      | importProduct           | P002737 |
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bpartner/001' and fulfills with '201' status code
    """
{
    "requestItems": [
        {
            "bpartnerIdentifier": "ext-Other-S0325",
            "externalVersion": null,
            "externalReferenceUrl": null,
            "isReadOnlyInMetasfresh": null,
            "bpartnerComposite": {
                "bpartner": {
                    "code": "ext-Other-S0325",
                    "active": true,
                    "name": "partnerNameS0325",
                    "companyName": "companyNameS0325",
                    "vendor": true,
                    "customer": true,
                    "phone": "+49 123456789",
                    "language": "de",
                    "priceListId": 2008396,
                    "vatId": "vatId_BPartner001"
                },
                "locations": {
                    "requestItems": [
                        {
                            "locationIdentifier": "ext-Other-S0325",
                            "externalVersion": null,
                            "isReadOnlyInMetasfresh": null,
                            "location": {
                                "active": true,
                                "name": ".",
                                "bpartnerName": null,
                                "address1": "address S0325",
                                "postal": "0325",
                                "city": "city S0325",
                                "countryCode": "DE",
                                "shipTo": true,
                                "shipToDefault": true,
                                "billTo": true,
                                "billToDefault": true
                            },
                            "externalSystemConfigId": null
                        }
                    ],
                    "syncAdvise": null
                },
                "contacts": {
                    "requestItems": [
                        {
                            "contactIdentifier": "ext-Other-S0325",
                            "isReadOnlyInMetasfresh": null,
                            "contact": {
                                "code": "ext-Other-S0325",
                                "active": true,
                                "name": "name S0325",
                                "phone": "+41 123456789"
                            },
                            "jsonAlbertaContact": null,
                            "externalSystemConfigId": null
                        }
                    ],
                    "syncAdvise": {
                        "ifNotExists": "CREATE",
                        "ifExists": "UPDATE_MERGE"
                    }
                }
            },
            "externalSystemConfigId": null
        }
    ],
    "syncAdvise": {
        "ifNotExists": "CREATE",
        "ifExists": "UPDATE_MERGE"
    }
}
"""
    Then verify that bPartner was created for externalIdentifier
      | C_BPartner_ID.Identifier | externalIdentifier | Name             |
      | created_bpartner         | ext-Other-S0325    | partnerNameS0325 |
    And verify that location was created for bpartner
      | bpartnerIdentifier | locationIdentifier | OPT.C_BPartner_Location_ID.Identifier | CountryCode |
      | ext-Other-S0325    | ext-Other-S0325    | created_location                      | DE          |
    And verify that contact was created for bpartner
      | bpartnerIdentifier | contactIdentifier | Name       | OPT.AD_User_ID.Identifier |
      | ext-Other-S0325    | ext-Other-S0325   | name S0325 | created_contact           |

  @from:cucumber
  @Id:S0325_100
  Scenario: Import sales I_Invoice_Candidate from csv
    Given metasfresh initially has no I_Invoice_Candidate import data
    And add HTTP header
      | Key          | Value      |
      | Content-Type | text/plain |
      | Accept       | */*        |
    And store String as requestBody in context
      | String                                                                                  |
      | S0325;S0325;S0325;P002737;16.10.2023;1;1;PCE;Y;ARI;;16.10.2023;S0325;S0325;;;;S0325;100 |

    When the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=InvoiceCandidateExternalReference&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '200' status code
    And I_Invoice_Candidate is found: searching by product value
      | M_Product_Value | I_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | Bill_User_ID.Identifier | M_Product_ID.Identifier | OPT.DateOrdered | QtyOrdered | AD_Org_ID.Identifier | OPT.QtyDelivered | OPT.C_UOM_ID.Identifier | IsSOTrx | OPT.C_DocType_ID.Identifier | OPT.PresetDateInvoiced | OPT.Description | OPT.POReference | I_IsImported |
      | P002737         | iInvoiceCandidate_1               | created_bpartner            | created_location            | created_contact         | importProduct           | 2023-10-16      | 1          | importFormatOrg      | 1                | importUOM               | Y       | importDocType               | 2023-10-16             | S0325           | S0325           | Y            |
    And validate invoice candidates by record reference:
      | TableName           | I_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | AD_Org_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.DateOrdered | OPT.QtyOrdered | OPT.QtyDelivered | OPT.C_UOM_ID.Identifier | IsSOTrx | OPT.C_DocType_ID.Identifier | OPT.PresetDateInvoiced | OPT.Description | OPT.POReference | InvoiceRule |
      | I_Invoice_Candidate | iInvoiceCandidate_1               | invoiceCandidate_1                | created_bpartner            | created_location            | importFormatOrg      | created_user                | importProduct               | 2023-10-16      | 1              | 1                | importUOM               | true    | importDocType               | 2023-10-16             | S0325           | S0325           | D           |
    # note: updating to invoicing rule immediate in order to verify the invoice creation step
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | invoiceCandidate_1                | I                        |
    And after not more than 60s C_Invoice_Candidate matches:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice |
      | invoiceCandidate_1                | 1                |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_1                |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_1                | invoice_1               |