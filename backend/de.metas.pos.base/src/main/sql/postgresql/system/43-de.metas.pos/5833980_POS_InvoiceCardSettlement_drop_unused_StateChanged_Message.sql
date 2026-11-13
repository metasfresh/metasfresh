-- Card settlement of an open invoice at the POS till: drop the AD_Message de.metas.pos.InvoiceCardSettlement.StateChanged
-- (545997, added by 5833650). No code uses it: the release confirmation returns the settlement's current state instead of
-- raising an error when the status changed meanwhile.

DELETE FROM AD_Message_Trl WHERE AD_Message_ID=545997
;

DELETE FROM AD_Message WHERE AD_Message_ID=545997
;
