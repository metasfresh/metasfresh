
UPDATE AD_AttachmentEntry SET tags=replace(tags, 'BelogsToExternalReference=','BelongsToExternalReference=') WHERE tags like '%BelogsToExternalReference=%';
