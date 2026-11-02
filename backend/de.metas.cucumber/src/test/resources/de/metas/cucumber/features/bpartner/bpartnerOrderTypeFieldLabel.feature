@from:cucumber
@ghActions:run_on_executor3
@allure.label.epic:E0390_Masterdata_Partner
@allure.label.feature:F00900_Business_Partner
Feature: The partner's order type field is labelled "order type" in every supported language

  ## The field C_BPartner.SO_DocTypeTarget_ID (AD_Field 542107, tab "Kunde") has its own name element, so the shared
  ## element "Zielbelegart" of the column and every other window stay unchanged.

  Background:
    Given infrastructure and metasfresh are running

  Scenario: AD_Field 542107 (C_BPartner.SO_DocTypeTarget_ID) is labelled Auftragsart / Order Type
    Then validate AD_Field labels:
      | AD_Field_ID | AD_Language | Name         |
      | 542107      | de_DE       | Auftragsart  |
      | 542107      | de_CH       | Auftragsart  |
      | 542107      | en_US       | Order Type   |
