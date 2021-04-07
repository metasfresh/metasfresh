package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductTrl;
import de.metas.procurement.webui.repository.ProductRepository;
import de.metas.procurement.webui.repository.ProductTrlRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@Transactional
class SyncProductImportService extends AbstractSyncImportService
{
	private final ProductRepository productsRepo;
	private final ProductTrlRepository productTrlsRepo;

	public SyncProductImportService(
			@NonNull final ProductRepository productsRepo,
			@NonNull final ProductTrlRepository productTrlsRepo)
	{
		this.productsRepo = productsRepo;
		this.productTrlsRepo = productTrlsRepo;
	}

	public Optional<Product> importProduct(final SyncProduct syncProduct)
	{
		final Product product = productsRepo.findByUuid(syncProduct.getUuid());
		return importProduct(syncProduct, product);
	}

	/**
	 * Imports the given <code>syncProduct</code>, updating the given <code>product</code> if feasible.
	 * If it's not feasible, then the method attempts to load the {@link Product} to update using the given <code>syncProduct</code>'s UUID.<br>
	 * If there is no such existing product, the method creates a new one.
	 *
	 * @param product the product to be updated. If <code>null</code> or if its UUID doesn't match the given <code>syncProduct</code>'s UUID, then this parameter is ignored.
	 */
	public Optional<Product> importProduct(
			@NonNull final SyncProduct syncProduct,
			@Nullable Product product)
	{
		final String uuid = syncProduct.getUuid();
		if (product != null && !Objects.equals(product.getUuid(), uuid))
		{
			product = null;
		}
		if (product == null)
		{
			product = productsRepo.findByUuid(uuid);
		}

		//
		// Handle delete request
		if (syncProduct.isDeleted())
		{
			if (product != null)
			{
				deleteProduct(product);
			}

			return Optional.empty();
		}

		if (product == null)
		{
			product = new Product();
			product.setUuid(uuid);
		}

		product.setDeleted(false);
		product.setName(syncProduct.getName());
		product.setPackingInfo(syncProduct.getPackingInfo());
		product.setShared(syncProduct.isShared());
		productsRepo.save(product);
		logger.debug("Imported: {} -> {}", syncProduct, product);

		//
		// Import product translations
		final Map<String, ProductTrl> productTrls = mapByLanguage(productTrlsRepo.findByRecord(product));
		for (final Map.Entry<String, String> lang2nameTrl : syncProduct.getNameTrls().entrySet())
		{
			final String language = lang2nameTrl.getKey();
			final String nameTrl = lang2nameTrl.getValue();

			ProductTrl productTrl = productTrls.remove(language);
			if (productTrl == null)
			{
				productTrl = new ProductTrl();
				productTrl.setLanguage(language);
				productTrl.setRecord(product);
			}

			productTrl.setName(nameTrl);
			productTrlsRepo.save(productTrl);
			logger.debug("Imported: {}", productTrl);
		}
		for (final ProductTrl productTrl : productTrls.values())
		{
			productTrlsRepo.delete(productTrl);
		}

		return Optional.of(product);
	}

	private void deleteProduct(final Product product)
	{
		if (product.isDeleted())
		{
			return;
		}
		product.setDeleted(true);
		productsRepo.save(product);

		logger.debug("Deleted: {}", product);
	}
}
