/*
 * Created on Jul 29, 2004
 *
 */
package net.sourceforge.fenixedu.applicationTier.Servico.manager.curricularCourseGroupsManagement;

import net.sourceforge.fenixedu.applicationTier.Service;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.dataTransferObject.InfoCurricularCourseGroup;
import net.sourceforge.fenixedu.dataTransferObject.InfoCurricularCourseGroupWithInfoBranch;
import net.sourceforge.fenixedu.domain.CurricularCourseGroup;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;

/**
 * @author Jo�o Mota
 * 
 */
public class ReadCurricularCourseGroup extends Service {

	public InfoCurricularCourseGroup run(Integer groupId) throws FenixServiceException, ExcepcaoPersistencia {
		CurricularCourseGroup group = rootDomainObject.readCurricularCourseGroupByOID(groupId);
		return InfoCurricularCourseGroupWithInfoBranch.newInfoFromDomain(group);
	}

}