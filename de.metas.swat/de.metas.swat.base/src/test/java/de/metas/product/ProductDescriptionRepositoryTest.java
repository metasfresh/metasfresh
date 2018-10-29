package de.metas.product;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductDescription;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.bpartner.BPartnerId;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class,
		ProductDescriptionRepository.class })
public class ProductDescriptionRepositoryTest
{
	private I_M_Product product;
	private I_C_BPartner bpartner;
	private final String text1 = "Id nostra sem tortor sollicitudin praesent senectus";
	private final String text2 = "Porttitor ut taciti pretium luctus nulla";
	private ProductDescriptionRepository repo;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue("Product value");
		product.setName("Product Name");
		save(product);

		bpartner = newInstanceOutOfTrx(I_C_BPartner.class);
		bpartner.setValue("bpartner value");
		bpartner.setName("bpartner Name");
		save(bpartner);

		final I_M_ProductDescription productDescription1 = newInstanceOutOfTrx(I_M_ProductDescription.class);
		productDescription1.setM_Product(product);
		productDescription1.setName(text1);
		save(productDescription1);

		final I_M_ProductDescription productDescription2 = newInstanceOutOfTrx(I_M_ProductDescription.class);
		productDescription2.setM_Product(product);
		productDescription2.setName(text2);
		productDescription2.setC_BPartner(bpartner);
		save(productDescription2);

		repo = Adempiere.getBean(ProductDescriptionRepository.class);

	}

	@Test
	public void testProductDescriptionWithNoGivenBPartner()
	{
		final ProductDescription productDescription = repo.getByProductIdAndBPartner(ProductId.ofRepoId(product.getM_Product_ID()), null);
		assertThat(productDescription.getName()).isEqualTo(text1);
	}

	@Test
	public void testProductDescriptionWitGivenhBPartner()
	{
		final ProductDescription productDescription = repo.getByProductIdAndBPartner(ProductId.ofRepoId(product.getM_Product_ID()), BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()));
		assertThat(productDescription.getName()).isEqualTo(text2);
	}

}
