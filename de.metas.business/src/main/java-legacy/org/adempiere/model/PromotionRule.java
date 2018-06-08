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
package org.adempiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Promotion;
import org.compiere.model.I_M_PromotionDistribution;
import org.compiere.model.I_M_PromotionLine;
import org.compiere.model.I_M_PromotionReward;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.order.IOrderLineBL;

/**
 *
 * @author hengsin
 *
 */
public class PromotionRule {

	public static void applyPromotions(MOrder order) throws Exception {
		//key = C_OrderLine, value = Qty to distribution
		Map<Integer, BigDecimal> orderLineQty = new LinkedHashMap<Integer, BigDecimal>();
		Map<Integer, MOrderLine> orderLineIndex = new HashMap<Integer, MOrderLine>();
		MOrderLine[] lines = order.getLines();
		boolean hasDeleteLine = false;
		for (MOrderLine ol : lines) {
			// metas: begin: delete if is a promotion line:
			if (ol.getM_Promotion_ID() > 0)
			{
				ol.deleteEx(true); // changed from false to true for US1252
				hasDeleteLine = true;
				continue;
			}
			// metas: end
			if (ol.getM_Product_ID() > 0) {
				if (ol.getQtyOrdered().signum() > 0) {
					orderLineQty.put(ol.getC_OrderLine_ID(), ol.getQtyOrdered());
					orderLineIndex.put(ol.getC_OrderLine_ID(), ol);
				}
			}
			/* metas: commented
			else if (ol.getC_Charge_ID() > 0) {
				Number id = (Number) ol.get_Value("M_Promotion_ID");
				if (id != null && id.intValue() > 0) {
					ol.delete(false);
					hasDeleteLine = true;
				}
			}
			*/
		}
		if (orderLineQty.isEmpty()) return;

		//refresh order
		if (hasDeleteLine) {
			order.getLines(true, null);
			order.getTaxes(true);
			order.setGrandTotal(DB.getSQLValueBD(order.get_TrxName(), "SELECT GrandTotal From C_Order WHERE C_Order_ID = ?", order.getC_Order_ID()));
		}

		Map<Integer, List<Integer>> promotions = PromotionRule.findM_Promotion_ID(order);

		if (promotions == null || promotions.isEmpty()) return;

		BigDecimal orderAmount = order.getGrandTotal();

		//key = M_PromotionDistribution_ID, value = C_OrderLine_ID and Qty
		Map<Integer, DistributionSet> distributions = new LinkedHashMap<Integer, DistributionSet>();

		//<M_PromotionDistribution_ID, DistributionSorting>
		Map<Integer, String> sortingType = new HashMap<Integer, String>();
		OrderLineComparator olComparator = new OrderLineComparator(orderLineIndex);
		//distribute order lines
		for (Map.Entry<Integer, List<Integer>> entry : promotions.entrySet()) {
			Query query = new Query(Env.getCtx(), MTable.get(order.getCtx(), I_M_PromotionDistribution.Table_ID),
					"M_PromotionDistribution.M_Promotion_ID = ? AND M_PromotionDistribution.IsActive = 'Y'", order.get_TrxName());
			query.setParameters(new Object[]{entry.getKey()});
			query.setOrderBy("SeqNo");
			List<MPromotionDistribution> list = query.<MPromotionDistribution>list();

			Query rewardQuery = new Query(Env.getCtx(), MTable.get(order.getCtx(), I_M_PromotionReward.Table_ID),
					"M_PromotionReward.M_Promotion_ID = ? AND M_PromotionReward.IsActive = 'Y'", order.get_TrxName());
			rewardQuery.setParameters(new Object[]{entry.getKey()});
			rewardQuery.setOrderBy("SeqNo");
			List<MPromotionReward> rewardList = rewardQuery.<MPromotionReward>list();

			List<MPromotionLine> promotionLines = new ArrayList<MPromotionLine>();
			for (Integer M_PromotionLine_ID : entry.getValue()) {
				MPromotionLine promotionLine = new MPromotionLine(order.getCtx(), M_PromotionLine_ID, order.get_TrxName());
				promotionLines.add(promotionLine);
			}
			while (true) {
				boolean hasDistributionSet = false;
				Set<Integer>promotionLineSet = new HashSet<Integer>();
				Set<Integer>mandatoryLineSet = new HashSet<Integer>();
				boolean mandatoryLineNotFound = false;
				List<Integer> validPromotionLineIDs = new ArrayList<Integer>();
				for (MPromotionLine promotionLine : promotionLines) {
					if (promotionLine.getM_PromotionGroup_ID() == 0 && promotionLine.getMinimumAmt() != null && promotionLine.getMinimumAmt().signum() >= 0) {
						if (orderAmount.compareTo(promotionLine.getMinimumAmt()) >= 0) {
							orderAmount = orderAmount.subtract(promotionLine.getMinimumAmt());
							validPromotionLineIDs.add(promotionLine.getM_PromotionLine_ID());
						} else if (promotionLine.isMandatoryPL()) {
							mandatoryLineNotFound = true;
							break;
						}
					}
				}
				if (mandatoryLineNotFound) {
					break;
				}
				for (MPromotionDistribution pd : list) {
					if (entry.getValue().contains(pd.getM_PromotionLine_ID())) {
						//sort available orderline base on distribution sorting type
						List<Integer> orderLineIdList = new ArrayList<Integer>();
						orderLineIdList.addAll(orderLineQty.keySet());
						if (pd.getDistributionSorting() != null) {
							Comparator<Integer> cmp = olComparator;
							if (pd.getDistributionSorting().equals(MPromotionDistribution.DISTRIBUTIONSORTING_Descending))
								cmp = Collections.reverseOrder(cmp);
							Collections.sort(orderLineIdList, cmp);
						}
						DistributionSet prevSet = distributions.get(pd.getM_PromotionDistribution_ID());
						DistributionSet distributionSet = PromotionRule.calculateDistributionQty(pd, prevSet, validPromotionLineIDs, orderLineQty, orderLineIdList, order.get_TrxName());
						if (distributionSet != null && distributionSet.setQty.signum() > 0) {
							hasDistributionSet = true;
							promotionLineSet.add(pd.getM_PromotionLine_ID());
						} else {
							if (pd.getM_PromotionLine().isMandatoryPL()) {
								mandatoryLineSet.add(pd.getM_PromotionLine_ID());
							}
						}
						distributions.put(pd.getM_PromotionDistribution_ID(), distributionSet);
						sortingType.put(pd.getM_PromotionDistribution_ID(), pd.getDistributionSorting());
					}
				}
				if (!hasDistributionSet)
					break;

				if (mandatoryLineSet != null) {
					mandatoryLineNotFound = false;
					for(Integer id : mandatoryLineSet) {
						if (!promotionLineSet.contains(id)) {
							mandatoryLineNotFound = true;
							break;
						}
					}
					if (mandatoryLineNotFound) {
						break;
					}
				}

				for (MPromotionReward pr : rewardList) {
					if (pr.isForAllDistribution()) {
						Collection<DistributionSet> all = distributions.values();
						BigDecimal totalPrice = BigDecimal.ZERO;
						for(DistributionSet distributionSet : all) {
							for(Map.Entry<Integer, BigDecimal> olMap : distributionSet.orderLines.entrySet()) {
								BigDecimal qty = olMap.getValue();
								int C_OrderLine_ID = olMap.getKey();
								for (MOrderLine ol : lines) {
									if (ol.getC_OrderLine_ID() == C_OrderLine_ID) {
										totalPrice = totalPrice.add(ol.getPriceActual().multiply(qty));
										break;
									}
								}
								distributionSet.orderLines.put(olMap.getKey(), BigDecimal.ZERO);
							}
						}
						BigDecimal discount = BigDecimal.ZERO;
						if (pr.getRewardType().equals(MPromotionReward.REWARDTYPE_AbsoluteAmount)) {
							if (pr.getAmount().compareTo(totalPrice) < 0) {
								discount = totalPrice.subtract(pr.getAmount());
							}
						} else if (pr.getRewardType().equals(MPromotionReward.REWARDTYPE_FlatDiscount)) {
							discount = pr.getAmount();
						} else if (pr.getRewardType().equals(MPromotionReward.REWARDTYPE_Percentage)) {
							discount = pr.getAmount().divide(BigDecimal.valueOf(100.00)).multiply(totalPrice);
						}
						if (discount.signum() > 0) {
							addDiscountLine(order, null, discount, BigDecimal.valueOf(1.00), pr.getC_Charge_ID(), pr.getM_Promotion());
						}
					} else {
						int M_PromotionDistribution_ID = pr.getM_PromotionDistribution_ID();
						if (!distributions.containsKey(M_PromotionDistribution_ID))
							continue;
						int targetDistributionID = M_PromotionDistribution_ID;
						if (!pr.isSameDistribution()) {
							targetDistributionID = pr.getM_TargetDistribution_ID();
							if (!distributions.containsKey(targetDistributionID))
								continue;
						}
						DistributionSet distributionSet = distributions.get(targetDistributionID);

						//sort by reward distribution sorting
						if (pr.getDistributionSorting() != null ) {
							Comparator<Integer> cmp = new OrderLineComparator(orderLineIndex);
							if (pr.getDistributionSorting().equals(MPromotionReward.DISTRIBUTIONSORTING_Descending))
								cmp = Collections.reverseOrder(cmp);
							Set<Integer> keySet = distributionSet.orderLines.keySet();
							List<Integer> keyList = new ArrayList<Integer>();
							keyList.addAll(keySet);
							Collections.sort(keyList, cmp);
							Map<Integer, BigDecimal>sortedMap = new LinkedHashMap<Integer, BigDecimal>();
							for(Integer id : keyList) {
								sortedMap.put(id, distributionSet.orderLines.get(id));
							}
							distributionSet.orderLines = sortedMap;
						}


						BigDecimal setBalance = distributionSet.setQty;
						BigDecimal toApply = pr.getQty();
						if (toApply == null || toApply.signum() == 0)
							toApply = BigDecimal.valueOf(-1.0);

						BigDecimal totalPrice  = BigDecimal.ZERO;
						final List<OrderLinePromotionCandidate> orderLineCandidates = new ArrayList<OrderLinePromotionCandidate>(); // metas

						for(Map.Entry<Integer, BigDecimal> olMap : distributionSet.orderLines.entrySet()) {
							BigDecimal qty = olMap.getValue();
							int C_OrderLine_ID = olMap.getKey();
							if (qty == null || qty.signum() <= 0)
								continue;
							if (qty.compareTo(setBalance) >= 0) {
								qty = setBalance;
								setBalance = BigDecimal.ZERO;
							} else {
								setBalance = setBalance.subtract(qty);
							}
							if (toApply.signum() > 0) {
								if (toApply.compareTo(qty) <= 0) {
									qty = toApply;
									toApply = BigDecimal.ZERO;
								} else {
									toApply = toApply.subtract(qty);
								}
								BigDecimal newQty = olMap.getValue();
								newQty = newQty.subtract(qty);
								distributionSet.orderLines.put(olMap.getKey(), newQty);
							}
							for (MOrderLine ol : lines) {
								if (ol.getC_OrderLine_ID() == C_OrderLine_ID) {
									if (pr.getRewardType().equals(MPromotionReward.REWARDTYPE_Percentage)) {
										BigDecimal priceActual = ol.getPriceActual();
										BigDecimal discount = priceActual.multiply(pr.getAmount().divide(BigDecimal.valueOf(100.00)));
										addDiscountLine(order, ol, discount, qty, pr.getC_Charge_ID(), pr.getM_Promotion());
									} else if (pr.getRewardType().equals(MPromotionReward.REWARDTYPE_FlatDiscount)) {
										addDiscountLine(order, ol, pr.getAmount(), BigDecimal.valueOf(1.00), pr.getC_Charge_ID(), pr.getM_Promotion());
									} else if (pr.getRewardType().equals(MPromotionReward.REWARDTYPE_AbsoluteAmount)) {
										BigDecimal priceActual = ol.getPriceActual();
										totalPrice = totalPrice.add(priceActual.multiply(qty));
										// metas: begin
										OrderLinePromotionCandidate olCandidate = new OrderLinePromotionCandidate();
										olCandidate.orderLine = ol;
										olCandidate.qty = qty;
										olCandidate.promotionReward = pr;
										orderLineCandidates.add(olCandidate);
										// metas: end
									}
								}
							}

							if (toApply.signum() == 0)
								break;
							if (setBalance.signum() == 0)
								break;
						}
						if (pr.getRewardType().equals(MPromotionReward.REWARDTYPE_AbsoluteAmount))  {
							if (pr.getAmount().compareTo(totalPrice) < 0) {
//								addDiscountLine(order, null, totalPrice.subtract(pr.getAmount()), BigDecimal.valueOf(1.00), pr.getC_Charge_ID(), pr.getM_Promotion());
								addDiscountLine(order, orderLineCandidates); // metas
							}
						}
					}
				}
			}
		}
	}

	private static void addDiscountLine(
			MOrder order,
			MOrderLine ol,
			BigDecimal discount,
			BigDecimal qty,
			int C_Charge_ID,
			I_M_Promotion promotion) throws Exception
	{
		MOrderLine nol = new MOrderLine(order.getCtx(), 0, order.get_TrxName());
		nol.setC_Order_ID(order.getC_Order_ID());

		Services.get(IOrderLineBL.class).setOrder(nol, order);

		nol.setC_Charge_ID(C_Charge_ID);
		nol.setQty(qty);
		if (discount.scale() > 2)
			discount = discount.setScale(2, BigDecimal.ROUND_HALF_UP);
		nol.setPriceActual(discount.negate());
		if (ol != null && Integer.toString(ol.getLine()).endsWith("0")) {
			for(int i = 0; i < 9; i++) {
				int line = ol.getLine() + i + 1;
				int r = DB.getSQLValue(order.get_TrxName(), "SELECT C_OrderLine_ID FROM C_OrderLine WHERE C_Order_ID = ? AND Line = ?", order.getC_Order_ID(), line);
				if (r <= 0) {
					nol.setLine(line);
					break;
				}
			}
		}
		String description = promotion.getName();
		if (ol != null)
			description += (", " + ol.getName());
		nol.setDescription(description);
		nol.set_ValueOfColumn("M_Promotion_ID", promotion.getM_Promotion_ID());
		if (promotion.getC_Campaign_ID() > 0) {
			nol.setC_Campaign_ID(promotion.getC_Campaign_ID());
		}
		if (!nol.save())
			throw new AdempiereException("Failed to add discount line to order");
	}

	/**
	 *
	 * @param order
	 * @return Map<M_Promotion_ID, List<M_PromotionLine_ID>>
	 * @throws Exception
	 */
	private static Map<Integer, List<Integer>> findM_Promotion_ID(MOrder order) throws Exception {
		String select = "SELECT M_Promotion.M_Promotion_ID From M_Promotion Inner Join M_PromotionPreCondition "
			+ " ON (M_Promotion.M_Promotion_ID = M_PromotionPreCondition.M_Promotion_ID)";

		String bpFilter = "M_PromotionPreCondition.C_BPartner_ID = ? OR M_PromotionPreCondition.C_BP_Group_ID = ? OR (M_PromotionPreCondition.C_BPartner_ID IS NULL AND M_PromotionPreCondition.C_BP_Group_ID IS NULL)";
		String priceListFilter = "M_PromotionPreCondition.M_PriceList_ID IS NULL OR M_PromotionPreCondition.M_PriceList_ID = ?";
		String warehouseFilter = "M_PromotionPreCondition.M_Warehouse_ID IS NULL OR M_PromotionPreCondition.M_Warehouse_ID = ?";
		String dateFilter = "M_PromotionPreCondition.StartDate <= ? AND (M_PromotionPreCondition.EndDate >= ? OR M_PromotionPreCondition.EndDate IS NULL)";

		//optional promotion code filter
		String promotionCode = (String)order.get_Value("PromotionCode");

		StringBuffer sql = new StringBuffer();
		sql.append(select)
			.append(" WHERE")
			.append(" (" + bpFilter + ")")
			.append(" AND (").append(priceListFilter).append(")")
			.append(" AND (").append(warehouseFilter).append(")")
			.append(" AND (").append(dateFilter).append(")");
		if (promotionCode != null && promotionCode.trim().length() > 0) {
			sql.append(" AND (M_PromotionPreCondition.PromotionCode = ?)");
		} else {
			sql.append(" AND (M_PromotionPreCondition.PromotionCode IS NULL)");
		}

		//optional activity filter
		int C_Activity_ID = order.getC_Activity_ID();
		if (C_Activity_ID > 0) {
			sql.append(" AND (M_PromotionPreCondition.C_Activity_ID = ? OR M_PromotionPreCondition.C_Activity_ID IS NULL)");
		} else {
			sql.append(" AND (M_PromotionPreCondition.C_Activity_ID IS NULL)");
		}

		sql.append(" AND (M_Promotion.AD_Client_ID in (0, ?))")
			.append(" AND (M_Promotion.AD_Org_ID in (0, ?))")
			.append(" AND (M_Promotion.IsActive = 'Y')")
			.append(" AND (M_PromotionPreCondition.IsActive = 'Y')")
			.append(" ORDER BY M_Promotion.PromotionPriority Desc ");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		//Key = M_Promotion_ID, value = List<M_PromotionLine_ID>
		Map<Integer, List<Integer>> promotions = new LinkedHashMap<Integer, List<Integer>>();
		try {
			int pindex = 1;
			stmt = DB.prepareStatement(sql.toString(), order.get_TrxName());
			stmt.setInt(pindex++, order.getC_BPartner_ID());
			stmt.setInt(pindex++, order.getC_BPartner().getC_BP_Group_ID());
			stmt.setInt(pindex++, order.getM_PriceList_ID());
			stmt.setInt(pindex++, order.getM_Warehouse_ID());
			stmt.setTimestamp(pindex++, order.getDateOrdered());
			stmt.setTimestamp(pindex++, order.getDateOrdered());
			if (promotionCode != null && promotionCode.trim().length() > 0) {
				stmt.setString(pindex++, promotionCode);
			}
			if (C_Activity_ID > 0) {
				stmt.setInt(pindex++, C_Activity_ID);
			}
			stmt.setInt(pindex++, order.getAD_Client_ID());
			stmt.setInt(pindex++, order.getAD_Org_ID());
			rs = stmt.executeQuery();
			while(rs.next()) {
				int M_Promotion_ID = rs.getInt(1);
				List<Integer> promotionLineIDs = findPromotionLine(M_Promotion_ID, order);
				if (!promotionLineIDs.isEmpty()) {
					promotions.put(M_Promotion_ID, promotionLineIDs);
				}
			}
		} finally {
			DB.close(rs, stmt);
		}

		return promotions;
	}

	/**
	 *
	 * @param distribution
	 * @param prevSet
	 * @param orderLineQty
	 * @param orderLineQty2
	 * @param orderLineIdList
	 * @param qtyAvailable
	 * @return Distribution Qty
	 * @throws Exception
	 */
	private static DistributionSet calculateDistributionQty(MPromotionDistribution distribution,
			DistributionSet prevSet, List<Integer> validPromotionLineIDs, Map<Integer, BigDecimal> orderLineQty, List<Integer> orderLineIdList, String trxName) throws Exception {

		String sql = "SELECT C_OrderLine.C_OrderLine_ID FROM M_PromotionLine"
			+ " INNER JOIN M_PromotionGroup ON (M_PromotionLine.M_PromotionGroup_ID = M_PromotionGroup.M_PromotionGroup_ID AND M_PromotionGroup.IsActive = 'Y')"
			+ " INNER JOIN M_PromotionGroupLine ON (M_PromotionGroup.M_PromotionGroup_ID = M_PromotionGroupLine.M_PromotionGroup_ID AND M_PromotionGroupLine.IsActive = 'Y')"
			+ " INNER JOIN C_OrderLine ON (M_PromotionGroupLine.M_Product_ID = C_OrderLine.M_Product_ID)"
			+ " WHERE M_PromotionLine.M_PromotionLine_ID = ? AND C_OrderLine.C_OrderLine_ID = ?"
			+ " AND M_PromotionLine.IsActive = 'Y'";

		DistributionSet distributionSet = new DistributionSet();
		List<Integer>eligibleOrderLineIDs = new ArrayList<Integer>();
		if (distribution.getM_PromotionLine().getM_PromotionGroup_ID() == 0) {
			if (validPromotionLineIDs.contains(distribution.getM_PromotionLine_ID())) {
				eligibleOrderLineIDs.addAll(orderLineIdList);
			}
		} else {
			for(int C_OrderLine_ID : orderLineIdList) {
				BigDecimal availableQty = orderLineQty.get(C_OrderLine_ID);
				if (availableQty.signum() <= 0) continue;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try {
					stmt = DB.prepareStatement(sql, trxName);
					stmt.setInt(1, distribution.getM_PromotionLine_ID());
					stmt.setInt(2, C_OrderLine_ID);
					rs = stmt.executeQuery();
					if (rs.next()) {
						eligibleOrderLineIDs.add(C_OrderLine_ID);
					}
				} catch (Exception e) {
					throw new AdempiereException(e.getLocalizedMessage(), e);
				} finally {
					DB.close(rs, stmt);
				}
			}
		}

		if (eligibleOrderLineIDs.isEmpty()) {
			distributionSet.setQty = BigDecimal.ZERO;
			return distributionSet;
		}

		BigDecimal compareQty = distribution.getQty();

		BigDecimal setQty = BigDecimal.ZERO;
		BigDecimal totalOrderLineQty = BigDecimal.ZERO;
		for (int C_OrderLine_ID : eligibleOrderLineIDs) {
			BigDecimal availableQty = orderLineQty.get(C_OrderLine_ID);
			if (availableQty.signum() <= 0) continue;
			totalOrderLineQty = totalOrderLineQty.add(availableQty);
		}
		int compare = totalOrderLineQty.compareTo(compareQty);
		boolean match = false;
		if (compare <= 0 && "<=".equals(distribution.getOperation())) {
			match = true;
		} else if (compare >= 0 && ">=".equals(distribution.getOperation())) {
			match = true;
		}
		if (match) {
			if (MPromotionDistribution.DISTRIBUTIONTYPE_Max.equals(distribution.getDistributionType())) {
				setQty = compare > 0 ? totalOrderLineQty : distribution.getQty();
			} else if (MPromotionDistribution.DISTRIBUTIONTYPE_Min.equals(distribution.getDistributionType())) {
				setQty = compare < 0 ? totalOrderLineQty : distribution.getQty();
			} else {
				setQty = compare > 0 ? totalOrderLineQty.subtract(distribution.getQty())
						: distribution.getQty().subtract(totalOrderLineQty);
			}
			distributionSet.setQty = setQty;
			while (setQty.signum() > 0) {
				if (prevSet != null) {
					BigDecimal recycleQty = BigDecimal.ZERO;
					for(Map.Entry<Integer, BigDecimal> entry : prevSet.orderLines.entrySet()) {
						if (entry.getValue().signum() > 0) {
							setQty = setQty.subtract(entry.getValue());
							distributionSet.orderLines.put(entry.getKey(), entry.getValue());
							recycleQty = recycleQty.add(entry.getValue());
						}
					}
					if (recycleQty.signum() > 0) {
						for (int C_OrderLine_ID : eligibleOrderLineIDs) {
							BigDecimal availableQty = orderLineQty.get(C_OrderLine_ID);
							if (availableQty.signum() <= 0) continue;
							if (availableQty.compareTo(recycleQty) < 0) {
								recycleQty = recycleQty.subtract(availableQty);
								orderLineQty.put(C_OrderLine_ID, BigDecimal.ZERO);
							} else {
								availableQty = availableQty.subtract(recycleQty);
								orderLineQty.put(C_OrderLine_ID, availableQty);
								recycleQty = BigDecimal.ZERO;
							}
							if (recycleQty.signum() <= 0)
								break;
						}
					}
					if (setQty.signum() == 0) break;
				}
				for (int C_OrderLine_ID : eligibleOrderLineIDs) {
					BigDecimal availableQty = orderLineQty.get(C_OrderLine_ID);
					if (availableQty.signum() <= 0) continue;
					if (availableQty.compareTo(setQty) < 0) {
						setQty = setQty.subtract(availableQty);
						distributionSet.orderLines.put(C_OrderLine_ID, availableQty);
						orderLineQty.put(C_OrderLine_ID, BigDecimal.ZERO);
					} else {
						availableQty = availableQty.subtract(setQty);
						distributionSet.orderLines.put(C_OrderLine_ID, setQty);
						orderLineQty.put(C_OrderLine_ID, availableQty);
						setQty = BigDecimal.ZERO;
					}
					if (setQty.signum() <= 0)
						break;
				}
			}
		}

		return distributionSet ;
	}

	/**
	 *
	 * @param promotion_ID
	 * @param order
	 * @return List<M_PromotionLine_ID>
	 * @throws SQLException
	 */
	private static List<Integer> findPromotionLine(int promotion_ID, MOrder order) throws SQLException {
		Query query = new Query(Env.getCtx(), MTable.get(order.getCtx(), I_M_PromotionLine.Table_ID), " M_PromotionLine.M_Promotion_ID = ? AND M_PromotionLine.IsActive = 'Y'", order.get_TrxName());
		query.setParameters(new Object[]{promotion_ID});
		List<MPromotionLine>plist = query.<MPromotionLine>list();
		//List<M_PromotionLine_ID>
		List<Integer>applicable = new ArrayList<Integer>();
		MOrderLine[] lines = order.getLines();
		for (MPromotionLine pl : plist) {
			boolean match = false;
			if (pl.getM_PromotionGroup_ID() > 0) {
				String sql = "SELECT DISTINCT C_OrderLine.C_OrderLine_ID FROM M_PromotionGroup INNER JOIN M_PromotionGroupLine"
					+ " ON (M_PromotionGroup.M_PromotionGroup_ID = M_PromotionGroupLine.M_PromotionGroup_ID AND M_PromotionGroupLine.IsActive = 'Y')"
					+ " INNER JOIN C_OrderLine ON (M_PromotionGroupLine.M_Product_ID = C_OrderLine.M_Product_ID)"
					+ " INNER JOIN M_PromotionLine ON (M_PromotionLine.M_PromotionGroup_ID = M_PromotionGroup.M_PromotionGroup_ID)"
					+ " WHERE M_PromotionLine.M_PromotionLine_ID = ? AND C_OrderLine.C_Order_ID = ?"
					+ " AND M_PromotionLine.IsActive = 'Y'"
					+ " AND M_PromotionGroup.IsActive = 'Y'";
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try {
					stmt = DB.prepareStatement(sql, order.get_TrxName());
					stmt.setInt(1, pl.getM_PromotionLine_ID());
					stmt.setInt(2, order.getC_Order_ID());
					rs = stmt.executeQuery();
					BigDecimal orderAmt = BigDecimal.ZERO;
					while(rs.next()) {
						if (pl.getMinimumAmt() != null && pl.getMinimumAmt().signum() > 0) {
							int C_OrderLine_ID = rs.getInt(1);
							for (MOrderLine ol : lines) {
								if (ol.getC_OrderLine_ID() == C_OrderLine_ID) {
									orderAmt = orderAmt.add(ol.getLineNetAmt());
									break;
								}
							}
							if (orderAmt.compareTo(pl.getMinimumAmt()) >= 0) {
								match = true;
								break;
							}
						} else {
							match = true;
							break;
						}
					}
				} finally {
					DB.close(rs, stmt);
				}
			} else if (pl.getMinimumAmt() != null && pl.getMinimumAmt().compareTo(order.getGrandTotal()) <= 0 ) {
				match = true;
			}
			if (!match && pl.isMandatoryPL()) {
				applicable.clear();
				break;
			}
			if (match)
				applicable.add(pl.getM_PromotionLine_ID());
		}
		return applicable;
	}

	static class DistributionSet {
		//<C_OrderLine_Id, DistributionQty>
		Map<Integer, BigDecimal> orderLines = new LinkedHashMap<Integer, BigDecimal>();
		BigDecimal setQty = BigDecimal.ZERO;
	}

	static class OrderLineComparator implements Comparator<Integer> {
		Map<Integer, MOrderLine> index;
		OrderLineComparator(Map<Integer, MOrderLine> olIndex) {
			index = olIndex;
		}

		@Override
		public int compare(Integer ol1, Integer ol2) {
			return index.get(ol1).getPriceActual().compareTo(index.get(ol2).getPriceActual());
		}
	}

	// metas: begin
	static class OrderLinePromotionCandidate {
		BigDecimal qty;
		MOrderLine orderLine;
		I_M_PromotionReward promotionReward;
	}

	private static void addDiscountLine(MOrder order, List<OrderLinePromotionCandidate> candidates) throws Exception
	{
		for (OrderLinePromotionCandidate candidate : candidates)
		{
			final String rewardMode = candidate.promotionReward.getRewardMode();
			if (MPromotionReward.REWARDMODE_Charge.equals(rewardMode))
			{
				BigDecimal discount = candidate.orderLine.getPriceActual().multiply(candidate.qty);
				discount = discount.subtract(candidate.promotionReward.getAmount());
				BigDecimal qty = Env.ONE;
				int C_Charge_ID = candidate.promotionReward.getC_Charge_ID();
				I_M_Promotion promotion = candidate.promotionReward.getM_Promotion();
				addDiscountLine(order, null, discount, qty, C_Charge_ID, promotion);
			}
			else if (MPromotionReward.REWARDMODE_SplitQuantity.equals(rewardMode))
			{
				if (candidate.promotionReward.getAmount().signum() != 0)
				{
					throw new AdempiereException("@NotSupported@ @M_PromotionReward@ @Amount@ (@RewardMode@:"+rewardMode+"@)");
				}
				MOrderLine nol = new MOrderLine(order);
				MOrderLine.copyValues(candidate.orderLine, nol);
				// task 09358: get rid of this; instead, update qtyReserved at one central place
				// nol.setQtyReserved(Env.ZERO);
				nol.setQtyDelivered(Env.ZERO);
				nol.setQtyInvoiced(Env.ZERO);
				nol.setQtyLostSales(Env.ZERO);
				nol.setQty(candidate.qty);
				nol.setPrice(Env.ZERO);
				nol.setDiscount(Env.ONEHUNDRED);
				setPromotion(nol, candidate.promotionReward.getM_Promotion());
				setNextLineNo(nol);
				nol.saveEx();
			}
			else
			{
				throw new AdempiereException("@NotSupported@ @RewardMode@ "+rewardMode);
			}
		}
	}
	private static void setPromotion(MOrderLine nol, I_M_Promotion promotion)
	{
		nol.addDescription(promotion.getName());
		nol.set_ValueOfColumn("M_Promotion_ID", promotion.getM_Promotion_ID());
		if (promotion.getC_Campaign_ID() > 0)
		{
			nol.setC_Campaign_ID(promotion.getC_Campaign_ID());
		}
	}
	private static void setNextLineNo(MOrderLine ol)
	{
		if (ol != null && Integer.toString(ol.getLine()).endsWith("0")) {
			for(int i = 0; i < 9; i++) {
				int line = ol.getLine() + i + 1;
				int r = DB.getSQLValue(ol.get_TrxName(), "SELECT C_OrderLine_ID FROM C_OrderLine WHERE C_Order_ID = ? AND Line = ?", ol.getC_Order_ID(), line);
				if (r <= 0) {
					ol.setLine(line);
					return;
				}
			}
		}

		ol.setLine(0);
	}
	// metas: end
}
