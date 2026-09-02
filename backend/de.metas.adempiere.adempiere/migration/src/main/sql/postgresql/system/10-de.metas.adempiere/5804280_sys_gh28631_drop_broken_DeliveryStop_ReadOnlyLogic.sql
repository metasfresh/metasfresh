-- gh#28631: Delivery / Order Stop — drop the broken @#AD_Role_Group ReadOnlyLogic from the two
-- new BPartner-window AD_Field rows.
--
-- Background: PR https://github.com/metasfresh/metasfresh/pull/22796 shipped with
-- ReadOnlyLogic='@#AD_Role_Group/''''@!Accounting' on AD_Field 774882 (IsDeliveryStop) and
-- 774883 (DeliveryStopReason) — intent: "editable only for the Accounting role group".
-- The @#AD_Role_Group context variable resolves via UserRolePermissions.getRoleGroup(),
-- which reads Role.roleGroup. But that field is never populated:
--   * the only path that would populate it (RoleDAO.toRole() line 87) is commented out
--   * the generated I_AD_Role has no getRole_Group() — no DB column to read from
--   * no migration in metasfresh / mf15-private-extensions / any customer repo adds the column
-- → @#AD_Role_Group is always null → '' != 'Accounting' is always TRUE → the field is
--   read-only for every user on every deployment, including the intended Accounting users.
--
-- Fix: drop the gate from core. Customer repos that want a per-role gate add their own
-- ReadOnlyLogic (using @#AD_Role_ID@ — which DOES work) in a customer-side migration.
-- See dt204 PR (companion) for the per-customer override on these same field IDs plus the
-- dt204 custom-window field IDs.

UPDATE AD_Field
SET ReadOnlyLogic = NULL,
    Updated = TO_TIMESTAMP('2026-05-23 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID IN (
    774882, -- IsDeliveryStop on AD_Window 123 (base Geschäftspartner)
    774883  -- DeliveryStopReason on AD_Window 123 (base Geschäftspartner)
);
