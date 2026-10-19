@from:cucumber
Feature: QR-bill payload uses structured addresses (address type S)
  Swiss QR-bill (implementation guidelines 2.3): creditor and ultimate debtor are structured addresses,
  i.e. address type "S", street and building number in separate lines, postal code and town in separate lines.
  Real shape: completed sales invoice in CHF, organization with a QR-IBAN account, reference type QRR.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh contains M_PricingSystems
      | Identifier | Name     | Value    |
      | ps_qr      | qrBillPS | qrBillPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name     | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_qr      | ps_qr                         | CH                        | CHF                 | qrBillPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_qr     | pl_qr                     | 2022-05-01 |
    And metasfresh contains M_Products:
      | Identifier | Name       |
      | p_qr       | qrBillProd |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_qr      | plv_qr                            | p_qr                    | 100.00   | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name        | OPT.CompanyName | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bp_qr      | QrBillKunde | QrBillKunde     | Y              | ps_qr                         |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1  | OPT.Postal | OPT.City  |
      | loc_qr                   | CH          | Marktgasse 28 | 9400       | Rorschach |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpl_qr     | bp_qr                    | loc_qr                       | Y                   | Y                   |

  # ----------------------------------------------------------------------------------------------------
  # Completed invoice, QR-IBAN account, QRR reference
  # ----------------------------------------------------------------------------------------------------
  @Id:S32986_TC11_1
  Scenario: invoice QR-bill payload has structured creditor and debtor addresses
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_qr_1   | bp_qr                    | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_qr_1  | inv_qr_1                | p_qr                    | 1           | PCE               |
    And the invoice identified by inv_qr_1 belongs to an organization that is the QR-bill creditor:
      | Name      | Address1          | Postal | City | CountryCode | IBAN                       | OPT.QR_IBAN                |
      | Muster AG | Bahnhofstrasse 12 | 3011   | Bern | CH          | CH93 0076 2011 6238 5295 7 | CH44 3199 9123 0008 8901 2 |
    And the invoice identified by inv_qr_1 is completed

    Then the SQL function de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_QR_Code called with the ID of inv_qr_1 returns these lines:
      | LineNo | Value                 |
      | 1      | SPC                   |
      | 2      | 0200                  |
      | 3      | 1                     |
      | 4      | CH4431999123000889012 |
      | 5      | S                     |
      | 6      | Muster AG             |
      | 7      | Bahnhofstrasse        |
      | 8      | 12                    |
      | 9      | 3011                  |
      | 10     | Bern                  |
      | 11     | CH                    |
      | 12     |                       |
      | 13     |                       |
      | 14     |                       |
      | 15     |                       |
      | 16     |                       |
      | 17     |                       |
      | 18     |                       |
      | 19     | <GrandTotal>          |
      | 20     | CHF                   |
      | 21     | S                     |
      | 22     | QrBillKunde           |
      | 23     | Marktgasse            |
      | 24     | 28                    |
      | 25     | 9400                  |
      | 26     | Rorschach             |
      | 27     | CH                    |
      | 28     | QRR                   |
      | 29     | ~\d{27}               |
      | 30     |                       |
      | 31     | EPD                   |
      | 32     |                       |

  # ----------------------------------------------------------------------------------------------------
  # Dunning letter on that invoice
  # ----------------------------------------------------------------------------------------------------
  @Id:S32986_TC11_2
  Scenario: dunning QR-bill payload has structured creditor and debtor addresses
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_qr_2   | bp_qr                    | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_qr_2  | inv_qr_2                | p_qr                    | 1           | PCE               |
    And the invoice identified by inv_qr_2 belongs to an organization that is the QR-bill creditor:
      | Name      | Address1          | Postal | City | CountryCode | IBAN                       | OPT.QR_IBAN                |
      | Muster AG | Bahnhofstrasse 12 | 3011   | Bern | CH          | CH93 0076 2011 6238 5295 7 | CH44 3199 9123 0008 8901 2 |
    And the invoice identified by inv_qr_2 is completed
    And a dunning letter identified by dun_qr_2 is created for the invoice identified by inv_qr_2

    Then the SQL function de_metas_endcustomer_fresh_reports.Docs_Sales_Dunning_QR_Code called with the ID of dun_qr_2 returns these lines:
      | LineNo | Value                 |
      | 1      | SPC                   |
      | 2      | 0200                  |
      | 3      | 1                     |
      | 4      | CH4431999123000889012 |
      | 5      | S                     |
      | 6      | Muster AG             |
      | 7      | Bahnhofstrasse        |
      | 8      | 12                    |
      | 9      | 3011                  |
      | 10     | Bern                  |
      | 11     | CH                    |
      | 12     |                       |
      | 13     |                       |
      | 14     |                       |
      | 15     |                       |
      | 16     |                       |
      | 17     |                       |
      | 18     |                       |
      | 19     | <GrandTotal>          |
      | 20     | CHF                   |
      | 21     | S                     |
      | 22     | QrBillKunde           |
      | 23     | Marktgasse            |
      | 24     | 28                    |
      | 25     | 9400                  |
      | 26     | Rorschach             |
      | 27     | CH                    |
      | 28     | QRR                   |
      | 29     | ~\d{27}               |
      | 30     |                       |
      | 31     | EPD                   |
      | 32     |                       |

  # ----------------------------------------------------------------------------------------------------
  # Creditor street without building number
  # ----------------------------------------------------------------------------------------------------
  @Id:S32986_TC11_3
  Scenario: creditor street without building number gives an empty building-number line
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_qr_3   | bp_qr                    | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_qr_3  | inv_qr_3                | p_qr                    | 1           | PCE               |
    And the invoice identified by inv_qr_3 belongs to an organization that is the QR-bill creditor:
      | Name      | Address1       | Postal | City | CountryCode | IBAN                       | OPT.QR_IBAN                |
      | Muster AG | Bahnhofstrasse | 3011   | Bern | CH          | CH93 0076 2011 6238 5295 7 | CH44 3199 9123 0008 8901 2 |
    And the invoice identified by inv_qr_3 is completed

    Then the SQL function de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_QR_Code called with the ID of inv_qr_3 returns these lines:
      | LineNo | Value                 |
      | 4      | CH4431999123000889012 |
      | 5      | S                     |
      | 6      | Muster AG             |
      | 7      | Bahnhofstrasse        |
      | 8      |                       |
      | 9      | 3011                  |
      | 10     | Bern                  |
      | 11     | CH                    |
      | 19     | <GrandTotal>          |
      | 21     | S                     |
      | 23     | Marktgasse            |
      | 24     | 28                    |
      | 25     | 9400                  |
      | 26     | Rorschach             |
      | 27     | CH                    |
      | 28     | QRR                   |
      | 29     | ~\d{27}               |
      | 31     | EPD                   |

  # ----------------------------------------------------------------------------------------------------
  # Organization account without QR-IBAN. Only the CR / UD blocks
  # change; account, amount, reference type, reference, message and trailer are what the function printed before.
  # ----------------------------------------------------------------------------------------------------
  @Id:S32986_TC11_4
  Scenario: organization account without QR-IBAN - only the address blocks differ
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_qr_4   | bp_qr                    | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_qr_4  | inv_qr_4                | p_qr                    | 1           | PCE               |
    And the invoice identified by inv_qr_4 belongs to an organization that is the QR-bill creditor:
      | Name      | Address1          | Postal | City | CountryCode | IBAN                       |
      | Muster AG | Bahnhofstrasse 12 | 3011   | Bern | CH          | CH93 0076 2011 6238 5295 7 |
    And the invoice identified by inv_qr_4 is completed

    Then the SQL function de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_QR_Code called with the ID of inv_qr_4 returns these lines:
      | LineNo | Value                 |
      | 1      | SPC                   |
      | 2      | 0200                  |
      | 3      | 1                     |
      | 4      | CH9300762011623852957 |
      | 5      | S                     |
      | 6      | Muster AG             |
      | 7      | Bahnhofstrasse        |
      | 8      | 12                    |
      | 9      | 3011                  |
      | 10     | Bern                  |
      | 11     | CH                    |
      | 19     | <GrandTotal>          |
      | 20     | CHF                   |
      | 21     | S                     |
      | 23     | Marktgasse            |
      | 24     | 28                    |
      | 25     | 9400                  |
      | 26     | Rorschach             |
      | 27     | CH                    |
      | 28     | NON                   |
      | 29     |                       |
      | 30     |                       |
      | 31     | EPD                   |
