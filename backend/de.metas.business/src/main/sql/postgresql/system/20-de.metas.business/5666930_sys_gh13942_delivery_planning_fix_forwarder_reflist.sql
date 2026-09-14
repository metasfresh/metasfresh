-- SUPERSEDED (net no-op): M_Forwarder was an early port design (gh13942), later REPLACED by the
-- standard M_Shipper and DROPPED within this same, still-unpublished port -- see gh14215: 5671880
-- (replace with M_Shipper), 5671920 (drop window), 5671930 (drop M_Forwarder table). Retained only
-- for migration-chain continuity; M_Forwarder does not ship.
--
-- Reference: M_Forwarder(Active)
-- Table: M_Forwarder
-- Key: M_Forwarder.M_Forwarder_ID
-- 2022-12-01T20:28:13.448Z
UPDATE AD_Ref_Table SET WhereClause='M_Forwarder.IsActive=''Y''',Updated=TO_TIMESTAMP('2022-12-01 21:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541694
;

-- Replaced by m_forwarder_id
ALTER TABLE m_delivery_planning
    DROP COLUMN forwarder
;
