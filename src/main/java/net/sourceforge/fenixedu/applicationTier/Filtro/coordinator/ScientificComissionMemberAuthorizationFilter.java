package net.sourceforge.fenixedu.applicationTier.Filtro.coordinator;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.applicationTier.Filtro.AuthorizationByRoleFilter;
import net.sourceforge.fenixedu.applicationTier.Filtro.exception.NotAuthorizedFilterException;
import net.sourceforge.fenixedu.domain.DegreeCurricularPlan;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.person.RoleType;
import net.sourceforge.fenixedu.injectionCode.AccessControl;

public class ScientificComissionMemberAuthorizationFilter extends AuthorizationByRoleFilter {

    @Override
    protected RoleType getRoleType() {
        return RoleType.COORDINATOR;
    }

    @Override
    public void execute(Object[] parameters) throws Exception {
        super.execute(parameters);

        IUserView userView = AccessControl.getUserView();
        Person person = userView.getPerson();

        // first argument must be a degree curricularPlan
        DegreeCurricularPlan degreeCurricularPlan = (DegreeCurricularPlan) parameters[0];

        if (!degreeCurricularPlan.getDegree().isMemberOfCurrentScientificCommission(person)) {
            throw new NotAuthorizedFilterException("degree.scientificCommission.notMember");
        }
    }

}
