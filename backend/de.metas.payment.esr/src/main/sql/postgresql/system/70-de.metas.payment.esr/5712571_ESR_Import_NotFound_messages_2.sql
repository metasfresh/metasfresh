update ad_tab_trl trl set NotFound_Message='-', NotFound_MessageDetail='Bitte haben Sie einen Moment Geduld, während Ihr Import verarbeitet wird und aktualisieren Sie die Seite nach wenigen Minuten.'
where trl.ad_tab_id=540442 and trl.ad_language='de_DE'
  and trl.NotFound_Message is null
;
