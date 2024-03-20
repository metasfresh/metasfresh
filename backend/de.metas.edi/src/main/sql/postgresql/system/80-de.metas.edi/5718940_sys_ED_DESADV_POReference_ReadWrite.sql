
-- EDI_DESADV.POReference was read-only to prevent accidental changes since 2017, but it should be as read-write as it is in all other documents, because sometimes it just has to be edited.
update ad_field set isreadonly='N', updatedby=99, updated='2024-03-08 16:02:00' where ad_field_id=555446;
