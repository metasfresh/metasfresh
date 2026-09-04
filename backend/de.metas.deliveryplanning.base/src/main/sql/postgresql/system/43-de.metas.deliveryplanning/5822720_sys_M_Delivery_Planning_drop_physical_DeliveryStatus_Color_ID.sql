-- M_Delivery_Planning.DeliveryStatus_Color_ID stops being a physical column so it can be derived
-- (ColumnSQL, see the AD_Column migration 5822730) from the very fact IsDelivered is derived from -
-- the planning's own M_InOut_ID. Stored, it drifted: the split never wrote it, leaving the sibling
-- plannings the split creates with no colour at all in the grid's first column.
--
-- Nothing is lost by dropping the values: the stored colour only ever held one of the two configured
-- palette colours, and which one is decided entirely by whether M_InOut_ID is set - which stays on the
-- row. The backup is defensive per the migration rules, not a restore path anyone needs.
--
-- Dependency sweep run before this script (views / functions / val-rules / virtual ColumnSQL /
-- EXP_FormatLine referencing deliverystatus_color_id on M_Delivery_Planning): NO hits. The only
-- ColumnSQL match anywhere is C_Invoice_Candidate.DeliveryStatusColor_ID, a different table's own
-- colour column that merely shares the word. There is also no FK constraint on the column.

SELECT backup_table('m_delivery_planning', '_31789_drop_DeliveryStatus_Color_ID');

SELECT db_alter_table('M_Delivery_Planning', 'ALTER TABLE M_Delivery_Planning DROP COLUMN DeliveryStatus_Color_ID');
