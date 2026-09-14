-- C_BPartner_EDI_Setting.SeqNo — per-partner auto-increment default.
--
-- Replaces the constant default 10 with a query default that yields the next
-- free step (max SeqNo of the partner's existing rows + 10). Every newly created
-- row — including the transient skeleton the WebUI inserts for a new grid row —
-- therefore gets a SeqNo distinct from the partner's existing rows, so the
-- uniqueness index on (SeqNo, C_BPartner_ID, location) never collides on row
-- creation (only on a deliberate SeqNo edit).
--
-- @SQL default is an application-layer default (evaluated on new-record); the
-- physical column default stays 10 for raw inserts. Reused: AD_Column 592791.

UPDATE AD_Column
SET DefaultValue = '@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_BPartner_EDI_Setting WHERE C_BPartner_ID=@C_BPartner_ID/-1@',
    Updated = TO_TIMESTAMP('2026-08-28 10:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 592791
;
