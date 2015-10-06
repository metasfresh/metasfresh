/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
var adempiere = {};
adempiere.store = new Persist.Store('UserToken'); 
adempiere.saveUserToken = function (key, hash, sessionId)
{
	adempiere.store.o.expires = 365;
	adempiere.store.set(key+".sid", sessionId);
	adempiere.store.o.expires = 365;
	adempiere.store.set(key+".hash", hash);	
}

adempiere.findUserToken = function (cmpid, key)
{
	var sid;

	var fsid = function(ok, val) {
		if (ok && !!val && !!sid)
		{
			var hash = val;
			zkau.send({uuid: cmpid, cmd: 'onUserToken', data: [sid, hash], ctl: true});
		}
	};
	
	var fhash = function(ok, val) {
      if (ok && !!val)
      {
    	  sid = val;
    	  adempiere.store.get(key+".hash", fsid);
      }      
    };
    
    adempiere.store.get(key+".sid", fhash);
}

adempiere.removeUserToken = function (key)
{
	adempiere.store.o.expires = -365;
	adempiere.store.set(key+".sid", "");
	adempiere.store.o.expires = -365;
	adempiere.store.set(key+".hash", "");	
}
