create table backup.AD_AttachmentEntry_ContentType as select AD_AttachmentEntry_ID, ContentType from AD_AttachmentEntry;
--select * from backup.AD_AttachmentEntry_ContentType;

update AD_AttachmentEntry set contentType=trim(substr(contentType, 0,strpos(contentType, ';'))) where strpos(contentType, ';') > 0;

