
UPDATE C_Country SET DisplaySequenceLocal=trim(replace(DisplaySequenceLocal, '@CO@', '')) where DisplaySequenceLocal like '%@CO@%';
