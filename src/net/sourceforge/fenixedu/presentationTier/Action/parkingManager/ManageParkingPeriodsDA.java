package net.sourceforge.fenixedu.presentationTier.Action.parkingManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.dataTransferObject.parking.ParkingCardsRenewalBean;
import net.sourceforge.fenixedu.domain.parking.ParkingRequestPeriod;
import net.sourceforge.fenixedu.presentationTier.Action.base.FenixDispatchAction;
import net.sourceforge.fenixedu.presentationTier.Action.resourceAllocationManager.utils.ServiceUtils;
import net.sourceforge.fenixedu.presentationTier.Action.resourceAllocationManager.utils.SessionUtils;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ManageParkingPeriodsDA extends FenixDispatchAction {

    public ActionForward prepareCardsRenewal(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	ParkingCardsRenewalBean parkingCardsRenewalBean = null;
	if (getRenderedObject("parkingCardsRenewalBean") != null) {
	    parkingCardsRenewalBean = (ParkingCardsRenewalBean) getRenderedObject("parkingCardsRenewalBean");
	} else {
	    parkingCardsRenewalBean = new ParkingCardsRenewalBean();
	}
	request.setAttribute("parkingCardsRenewalBean", parkingCardsRenewalBean);
	return mapping.findForward("cardsRenewal");
    }

    public ActionForward renewCards(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	ParkingCardsRenewalBean parkingCardsRenewalBean = (ParkingCardsRenewalBean) getRenderedObject("parkingCardsRenewalBean");
	request.setAttribute("parkingCardsRenewalBean", parkingCardsRenewalBean);
	String choosenGroupName = parkingCardsRenewalBean.getParkingGroup().getGroupName();
	if (!choosenGroupName.equalsIgnoreCase("Docentes")
		&& !choosenGroupName.equalsIgnoreCase("N�o Docentes")
		&& !choosenGroupName.equalsIgnoreCase("Especiais")
		&& !choosenGroupName.equalsIgnoreCase("Limitados")) {
	    setError(request, "error.cannot.renew.group");	    
	    return mapping.findForward("cardsRenewal");
	}
	if(!areValidDates(parkingCardsRenewalBean)) {
	    setError(request, "error.oldDate.biggerThan.newDate");	    
	    return mapping.findForward("cardsRenewal");
	}
	parkingCardsRenewalBean.getNotRenewedParkingParties().clear();
	parkingCardsRenewalBean = (ParkingCardsRenewalBean) ServiceUtils.executeService(SessionUtils
		.getUserView(request), "RenewParkingCards", new Object[] { parkingCardsRenewalBean });
	parkingCardsRenewalBean.orderNotRenewParkingParties();
	request.setAttribute("parkingCardsRenewalBean", parkingCardsRenewalBean);
	return mapping.findForward("cardsRenewal");
    }

    private boolean areValidDates(ParkingCardsRenewalBean parkingCardsRenewalBean) {
	return !parkingCardsRenewalBean.getActualEndDate().isAfter(parkingCardsRenewalBean.getRenewalEndDate());
    }

    public ActionForward prepareManageRequestsPeriods(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {
	List<ParkingRequestPeriod> parkingRequestPeriods = new ArrayList<ParkingRequestPeriod>(
		rootDomainObject.getParkingRequestPeriods());
	Collections.sort(parkingRequestPeriods, new BeanComparator("beginDate"));
	request.setAttribute("parkingRequestPeriods", parkingRequestPeriods);
	return mapping.findForward("manageRequestsPeriods");
    }

    public ActionForward editRequestPeriod(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {
	Integer parkingRequestPeriodToEditCode = new Integer(request.getParameter("idInternal"));
	request.setAttribute("parkingRequestPeriodToEdit", rootDomainObject
		.readParkingRequestPeriodByOID(parkingRequestPeriodToEditCode));
	return prepareManageRequestsPeriods(mapping, actionForm, request, response);
    }

    public ActionForward deleteRequestPeriod(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {
	Integer parkingRequestPeriodToDeleteCode = new Integer(request.getParameter("idInternal"));
	ServiceUtils.executeService(SessionUtils.getUserView(request), "DeleteParkingRequestPeriod",
		new Object[] { parkingRequestPeriodToDeleteCode });
	return prepareManageRequestsPeriods(mapping, actionForm, request, response);
    }
    
    private void setError(HttpServletRequest request, String errorMsg) {
	ActionMessages actionMessages = getMessages(request);
	actionMessages.add("error", new ActionMessage(errorMsg));
	saveMessages(request, actionMessages);
    }
}