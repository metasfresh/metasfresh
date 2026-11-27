@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
@ghActions:run_on_executor7
Feature: Manual transport order
  A transport order flagged as manual order can be completed without lines; without the flag the line check stays.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Shipper:
      | Identifier  | Name |
      | shipper_DHL | Dhl  |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer |
      | carrier    | Y            | N              |

  @Id:S33915_TC1
  Scenario: A manual transport order without lines can be completed, reactivated, completed again and closed
    Given metasfresh contains Transport Order
      | Identifier | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection | OPT.IsManualOrder |
      | to_manual  | shipper_DHL  | carrier             | carrier             | Outgoing           | true              |

    When the M_ShipperTransportation identified by to_manual is completed
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | to_manual | shipper_DHL | carrier | carrier | CO |

    When the M_ShipperTransportation identified by to_manual is reactivated
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | to_manual | shipper_DHL | carrier | carrier | IP |

    When the M_ShipperTransportation identified by to_manual is completed
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | to_manual | shipper_DHL | carrier | carrier | CO |

    When the M_ShipperTransportation identified by to_manual is closed
    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | to_manual | shipper_DHL | carrier | carrier | CL |

    And the following PL/pgSQL function is created or replaced in the DB:
      """
      DO $$
      DECLARE v_id numeric;
      BEGIN
        SELECT M_ShipperTransportation_ID INTO v_id
        FROM M_ShipperTransportation
        WHERE M_Shipper_ID = (SELECT M_Shipper_ID FROM M_Shipper WHERE Name = 'Dhl')
          AND DocStatus = 'CL' AND IsManualOrder = 'Y'
        ORDER BY Created DESC, M_ShipperTransportation_ID DESC
        LIMIT 1;
        IF v_id IS NULL THEN
          RAISE EXCEPTION 'ASSERT: no closed manual transport order found';
        END IF;
        IF EXISTS (SELECT 1 FROM C_Invoice_Candidate WHERE AD_Table_ID = get_table_id('M_ShipperTransportation') AND Record_ID = v_id) THEN
          RAISE EXCEPTION 'ASSERT: a manual transport order without lines must not get an invoice candidate';
        END IF;
      END
      $$
      """

  @Id:S33915_TC3
  Scenario: A transport order without the flag and without lines is still refused
    Given metasfresh contains Transport Order
      | Identifier | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | to_plain   | shipper_DHL  | carrier             | carrier             | Outgoing           |

    When completing the M_ShipperTransportation identified by to_plain is refused:
      | ErrorAdMessage |
      | NoLines        |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | to_plain | shipper_DHL | carrier | carrier | DR |
