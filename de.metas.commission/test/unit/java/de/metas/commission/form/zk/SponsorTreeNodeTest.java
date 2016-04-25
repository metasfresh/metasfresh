package de.metas.commission.form.zk;

import org.junit.Test;

import de.metas.commission.form.zk.SponsorTreeNode;

import junit.framework.Assert;

public class SponsorTreeNodeTest
{
	@Test
	public void test_getSponsorTypeName() throws Exception
	{
//		assertSponsorTypeName(isSalesRep, isCustomer, isProspect, expectedSponsorTypeName)
		assertSponsorTypeName(false,	false,	false,	"");
		assertSponsorTypeName(false,	false,	true,	SponsorTreeNode.SPONSORTYPENAME_INT);
		assertSponsorTypeName(false,	true,	false,	SponsorTreeNode.SPONSORTYPENAME_ENDK);
		assertSponsorTypeName(false,	true,	true,	SponsorTreeNode.SPONSORTYPENAME_INT);
		assertSponsorTypeName(true,		false,	false,	SponsorTreeNode.SPONSORTYPENAME_VP);
		assertSponsorTypeName(true,		false,	true,	SponsorTreeNode.SPONSORTYPENAME_VP);
		assertSponsorTypeName(true,		true,	false,	SponsorTreeNode.SPONSORTYPENAME_VP);
		assertSponsorTypeName(true,		true,	true,	SponsorTreeNode.SPONSORTYPENAME_VP);
	}
	
	private void assertSponsorTypeName(boolean isSalesRep, boolean isCustomer, boolean isProspect, String expectedSponsorTypeName)
	{
		SponsorTreeNode n = new SponsorTreeNode(
				-1, //sponsor_id,
				null, //sponsorNo,
				isSalesRep,
				-1, //bpartner_id,
				null, //bpName,
				isProspect,
				isCustomer);
		String actualSponsorTypeName = n.getSponsorTypeName();
		Assert.assertEquals("Invalid sponsor type name for "
				+" IsSalesRep="+isSalesRep
				+", IsCustomer="+isCustomer
				+", IsProspect="+isProspect,
				expectedSponsorTypeName,
				actualSponsorTypeName);
	}
}
