package net.sourceforge.fenixedu.applicationTier.Filtro.spaceManager;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.applicationTier.Filtro.Filtro;
import net.sourceforge.fenixedu.applicationTier.Filtro.exception.NotAuthorizedFilterException;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.space.AllocatableSpace;
import net.sourceforge.fenixedu.injectionCode.AccessControl;

public class SpaceAdministratorAuthorizationFilter extends Filtro {

    public void execute(Object[] parameters) throws Exception {
        IUserView userView = AccessControl.getUserView();
        if (userView != null) {
            Person person = userView.getPerson();
            if (!AllocatableSpace.personIsSpacesAdministrator(person)) {
                throw new NotAuthorizedFilterException();
            }
        } else {
            throw new NotAuthorizedFilterException();
        }
    }

}
