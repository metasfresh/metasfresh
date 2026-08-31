package de.metas.handlingunits.shipping;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.product.PackageDimensions;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.mpackage.Package;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import java.util.List;
import java.util.Set;

public interface IHUPackageBL extends ISingletonService
{
	/**
	 * Unassign all HUs from given package
	 */
	void destroyHUPackage(I_M_Package mpackage);

	void destroyHUPackages(Set<HuId> huId);

	List<PackageId> retrievePackageIds(HuId huId);

	/**
	 * Creates M_Package and an {@link I_M_Package_HU}
	 */
	I_M_Package createM_Package(CreatePackageForHURequest request);

	/**
	 * Like {@link #createM_Package(CreatePackageForHURequest)} but yields ONE M_Package per unit for a loose CU
	 * (a top-level VIRTUAL HU — {@code HU_UnitType=V} — single product, integer quantity) — 1 label per CU. All
	 * other HUs (cartons LU/TU, aggregate, multi-product, non-integer qty) yield a single-element list, identical
	 * to {@link #createM_Package}.
	 */
	List<I_M_Package> createM_Packages(CreatePackageForHURequest request);

	void assignPackageToHuId(@NonNull Package aPackage, @NonNull HuId huId);

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
	 * Checks if given HU is suitable for adding to shipper transportation.
	 *
	 * @return true if HU is eligible for adding to shipper transportation.
	 */
	boolean isEligibleForAddingToShipperTransportation(@NonNull I_M_HU hu);

	@NonNull
	PackageDimensions getPackageDimensions(@NonNull I_M_HU hu);
}
