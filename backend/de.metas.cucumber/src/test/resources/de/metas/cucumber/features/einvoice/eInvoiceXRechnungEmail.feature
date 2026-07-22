@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00751
@topic:eInvoiceXRechnungEmail
@ghActions:run_on_executor4
Feature: XRechnung e-invoice generated, attached and emailed on sales-invoice completion
## F00751: E-Invoicing
  When a sales invoice for an XRechnung recipient is completed, the C_Invoice AFTER_COMPLETE
  interceptor generates and KoSIT-validates the XRechnung XML, attaches it tagged Send_via_Email,
  and the document-outbound mail pipeline sends it from the configured billing mailbox.
  This scenario asserts the mail actually arrives in the running Mailpit.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService

    # The invoice runs under a dedicated org whose AD_OrgInfo.Org_BPartner_ID is a BR-DE-conformant
    # bpartner. The CII mapper resolves the seller from the invoice org's org-bpartner
    # (bPartnerDAO.retrieveOrgBPartner(invoice.AD_Org_ID)), so a dedicated org makes the test
    # independent of the seed DB's default org-bpartner. Mail is routed for this org too.
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Value    | Name                 |
      | sellerOrg            | EISELLER | E-Invoice Seller Org |
    And metasfresh contains M_Products:
      | Identifier | Name        |
      | product    | Testprodukt |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps_1       | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_1                          | DE                        | EUR                 | price_list_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_so     | pl_so                     | plv_so | 2022-01-30 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product | plv_so                            | product                 | 100.0    | Normal                        | PCE               |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | EN16931VATCategory |
      | tax19      | Normal                        | 19   | S                  |
    # Create a payment term (immediate, NetDays=0) and reference it by identifier on the invoice.
    # A clean CI DB has no fixed C_PaymentTerm_ID, so a hardcoded id would violate the FK.
    And metasfresh contains C_PaymentTerm
      | Identifier  | NetDays |
      | paymentTerm | 0       |

    # ── A BR-DE-conformant bpartner (VAT id, DE postal address, default contact (name/phone/email →
    #    C_BPartner.EMail virtual = BT-34), IBAN), linked below as sellerOrg's org-bpartner. ──
    # AD_OrgBP_ID links this bpartner to sellerOrg — bPartnerDAO.retrieveOrgBPartner() (used by the CII
    # mapper for the seller) resolves the org-bpartner via C_BPartner.AD_OrgBP_ID, not AD_OrgInfo.
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Value | Name        | CompanyName | M_PricingSystem_ID | VATaxID     | AD_OrgBP_ID.Identifier |
      | seller_org_bp | dt    | Muster GmbH | Muster GmbH | ps_1               | DE136695976 | sellerOrg              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | C_Country_ID | City   | Postal | Address1       |
      | seller_location | 4099999000001 | seller_org_bp            | true         | true         | DE           | Berlin | 10115  | Musterstraße 1 |
    # Seller contact: its email is the source of C_BPartner.EMail (a virtual column reading the
    # default contact), which the CII mapper uses for the seller electronic address (BT-34, BR-DE / PEPPOL-R020).
    And metasfresh contains AD_Users:
      | Identifier     | Name           | OPT.EMail                | OPT.Phone        | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | IsDefaultContact |
      | seller_contact | Max Mustermann | max.mustermann@muster.de | +49 30 123456789 | seller_org_bp                | seller_location                       | Y                |
    And metasfresh contains C_BP_BankAccount
      | Identifier  | C_BPartner_ID | C_Currency_ID | IBAN                   |
      | seller_bank | seller_org_bp | EUR           | DE89370400440532013000 |
    # Link the conformant bpartner as sellerOrg's org-bpartner — this is what the CII mapper reads as the seller.
    And metasfresh contains AD_OrgInfo:
      | AD_OrgInfo_ID.Identifier | AD_Org_ID.Identifier | Org_BPartner_ID.Identifier | OrgBP_Location_ID.Identifier |
      | sellerOrgInfo            | sellerOrg            | seller_org_bp              | seller_location              |

    # ── Billing mailbox + routing of sales-invoice (ARI) mail to it; SMTP points at Mailpit via $env ──
    And metasfresh contains AD_MailBox:
      | AD_MailBox_ID.Identifier | EMail                    | SMTPHost$env   | SMTPPort$env   | IsSmtpAuthorization | UserName$env   | Password$env       |
      | billingMailbox           | billing@metasfresh.local | TEST_SMTP_HOST | TEST_SMTP_PORT | Y                   | TEST_SMTP_USER | TEST_SMTP_PASSWORD |
    And metasfresh contains AD_MailConfig:
      | AD_MailBox_ID.Identifier | DocBaseType | AD_Org_ID.Identifier |
      | billingMailbox           | ARI         | sellerOrg            |
    # Deactivate any seed/customer ARI mail routings (e.g. pointing at an unreachable SMTP host) so the
    # sales-invoice mail resolves to the test's billing mailbox (isolation belongs in setUp).
    And AD_MailConfig routing for DocBaseType "ARI" is restricted to mailbox "billingMailbox"
    And update C_Doc_Outbound_Config IsAutoSendDocument:
      | TableName | IsAutoSendDocument |
      | C_Invoice | true               |

  Scenario: valid XRechnung recipient → XML emailed from billing mailbox
    # ── Buyer is an XRechnung e-invoice recipient with a Leitweg-ID (BT-10) and a bill address + contact email ──
    Given metasfresh contains C_BPartners:
      | Identifier | Name      | M_PricingSystem_ID.Identifier | OPT.IsCustomer | IsEInvoiceRecipeint | EInvoiceType | EInvoice_BuyerReference |
      | buyer      | Käufer AG | ps_1                          | Y              | Y                   | X            | 991-1234512345-06       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | C_Country_ID | City    | Postal | Address1    | OPT.EMail          |
      | buyer_location | 4088888000002 | buyer                    | true         | true         | DE           | Hamburg | 20095  | Käuferweg 5 | einkauf@kaeufer.de |
    And metasfresh contains AD_Users:
      | Identifier | Name        | OPT.EMail          | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | buyer_user | Buyer Clerk | einkauf@kaeufer.de | buyer                        | buyer_location                        |

    And metasfresh contains C_Invoice:
      | Identifier | IsSOTrx | AD_Org_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | M_PriceList_ID.Identifier | C_DocTypeTarget_ID.Name | C_PaymentTerm_ID.Identifier | DateInvoiced | C_Currency_ID | PaymentRule |
      | invoice    | true    | sellerOrg            | buyer                    | buyer_location                    | buyer_user            | pl_so                     | Ausgangsrechnung        | paymentTerm                 | 2022-02-01   | EUR           | T           |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 | Price | C_Tax_ID$set |
      | invoiceLine | invoice                 | product                 | 1           | PCE               | 100   | tax19        |
    # No explicit C_InvoiceTax row: completion (MInvoice.calculateTaxTotal) deletes and rebuilds the
    # C_InvoiceTax VAT breakdown (BG-23) from the invoice lines, so a pre-created row would be wiped.
    # The tax19 line above is what drives the rebuilt C_InvoiceTax the XRechnung mapper reads.

    And mailpit inbox is cleared

    # The C_Invoice AFTER_COMPLETE interceptor generates + KoSIT-validates the XRechnung and attaches it.
    And the invoice identified by invoice is completed

    # The doc-outbound + mail-send workpackages run and send the attachment via the billing mailbox.
    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.C_BPartner_ID.Identifier | OPT.DocBaseType | OPT.DocStatus |
      | invoiceOutboundLog               | invoice              | C_Invoice     | buyer                        | ARI             | CO            |

    Then mailpit received an email from "billing@metasfresh.local" with the xrechnung attachment of invoice "invoice" containing "xeinkauf.de:kosit:xrechnung_3.0"

  Scenario: silent factoring → XRechnung BT-84 carries the factorer's IBAN, not the seller's
    # ── The buyer's receivables are silently assigned (stille Zession) to a factor: the buyer
    #    C_BPartner is IsFactoring=Y, and the org has one IsFactorer=Y C_BPartner with its own
    #    default bank account. The CII mapper must emit the factorer's IBAN in BT-84 instead of
    #    the seller's, without disclosing the assignment (no BG-10 PayeeTradeParty). ──
    Given metasfresh contains C_BPartners without locations:
      | Identifier | Name           | M_PricingSystem_ID.Identifier | AD_Org_ID.Identifier | IsFactorer |
      | factorer   | Factoring GmbH | ps_1                          | sellerOrg            | Y          |
    And metasfresh contains C_BP_BankAccount
      | Identifier    | C_BPartner_ID | C_Currency_ID | IBAN                   |
      | factorer_bank | factorer      | EUR           | DE12500105170648489890 |

    Given metasfresh contains C_BPartners:
      | Identifier | Name       | M_PricingSystem_ID.Identifier | OPT.IsCustomer | IsEInvoiceRecipeint | EInvoiceType | EInvoice_BuyerReference | IsFactoring |
      | buyer      | Faktura AG | ps_1                          | Y              | Y                   | X            | 991-1234512345-07       | Y           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | C_Country_ID | City    | Postal | Address1      | OPT.EMail          |
      | buyer_location | 4088888000003 | buyer                    | true         | true         | DE           | Hamburg | 20095  | Faktoreiweg 7 | einkauf@faktura.de |
    And metasfresh contains AD_Users:
      | Identifier | Name          | OPT.EMail          | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | buyer_user | Faktura Clerk | einkauf@faktura.de | buyer                        | buyer_location                        |

    And metasfresh contains C_Invoice:
      | Identifier | IsSOTrx | AD_Org_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier | M_PriceList_ID.Identifier | C_DocTypeTarget_ID.Name | C_PaymentTerm_ID.Identifier | DateInvoiced | C_Currency_ID | PaymentRule |
      | invoice    | true    | sellerOrg            | buyer                    | buyer_location                    | buyer_user            | pl_so                     | Ausgangsrechnung        | paymentTerm                 | 2022-02-01   | EUR           | T           |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 | Price | C_Tax_ID$set |
      | invoiceLine | invoice                 | product                 | 1           | PCE               | 100   | tax19        |

    And mailpit inbox is cleared

    # The C_Invoice AFTER_COMPLETE interceptor resolves the payee IBAN: bill partner IsFactoring=Y
    # routes BT-84 to the org's IsFactorer=Y bpartner's default bank account (never the seller's).
    And the invoice identified by invoice is completed

    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.C_BPartner_ID.Identifier | OPT.DocBaseType | OPT.DocStatus |
      | invoiceOutboundLog               | invoice              | C_Invoice     | buyer                        | ARI             | CO            |

    # BT-84 (PayeePartyCreditorFinancialAccount/IBANID) carries the factorer's IBAN — visibly
    # distinct from the seller's IBAN (DE89370400440532013000) — proving the factorer's account,
    # not the seller's, was resolved for this silently-factored invoice.
    Then mailpit received an email from "billing@metasfresh.local" with the xrechnung attachment of invoice "invoice" containing "DE12500105170648489890"
