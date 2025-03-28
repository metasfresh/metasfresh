package de.metas.handlingunits.shipping;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.mpackage.PackageId;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import java.util.Collection;

public interface IHUPackageBL extends ISingletonService
{
	/**
	 * Unassign all HUs from given package
	 */
	void destroyHUPackage(I_M_Package mpackage);

	/**
	 * Creates M_Package and an {@link I_M_Package_HU}
	 */
	I_M_Package createM_Package(CreatePackageForHURequest request);

	/**
	 * Update all {@link I_M_Package}s and {@link I_M_ShippingPackage}s which are linked to given <code>hu</code>.
	 */
	void assignShipmentToPackages(final I_M_HU hu, final I_M_InOut inout, String trxName);

	/**
	 * Unassign given shipment from {@link I_M_Package}s and {@link I_M_ShippingPackage}s.
	 * This is the counterpart method of {@link #assignShipmentToPackages(I_M_HU, I_M_InOut, String)}.
	 */
	void unassignShipmentFromPackages(I_M_InOut shipment);

	/**
	 * @return the {@code POReference}-values of the given  {@code M_Packages}, separated by comma.
	 */
	@NonNull
	String getPOReference(@NonNull Collection<PackageId> packageIds);
}
