package net.sourceforge.fenixedu.util;
import java.util.List;

import net.sourceforge.fenixedu.domain.studentCurricularPlan.Specialization;

import org.apache.struts.util.LabelValueBean;

/**
 * @author Ricardo Clerigo & Telmo Nabais
 */

public class PrintAllCandidatesFilterType extends FenixUtil {
	
	// filtros iniciais
	private static Object[] filters = {
			new LabelValueBean("Mostrar todos os registos",PrintAllCandidatesFilter.INVALID_FILTER.getName()),
			new LabelValueBean("Especializa��o",PrintAllCandidatesFilter.FILTERBY_SPECIALIZATION_VALUE.getName()),
			new LabelValueBean("Situa��o",PrintAllCandidatesFilter.FILTERBY_SITUATION_VALUE.getName()),
			new LabelValueBean("Prentende dar aulas",PrintAllCandidatesFilter.FILTERBY_GIVESCLASSES_VALUE.getName()),
			new LabelValueBean("N�o prentende dar aulas",PrintAllCandidatesFilter.FILTERBY_DOESNTGIVESCLASSES_VALUE.getName())
	};

	// filtros a aplicar de especializacao
	private static Object[] specializationFilters = {
			new LabelValueBean("Mestrado",Specialization.MASTER_DEGREE.getName()),
			new LabelValueBean("Especializacao",Specialization.SPECIALIZATION.getName())
	};
	
	// filtros de situacao
	private static List situationNameFilters = SituationName.toArrayList();
	
	/** retorna a lista dos filtros como labelvaluebeans **/
	public static Object[] getMainFiltersAsList() {
		return filters;
	}
	
	/** retorna a lista dos filtros de especializacao como labelvaluebeans **/
	public static Object[] getSpecializationFiltersAsList() {
		return specializationFilters;
	}

	/** retorna a lista dos filtros de situacao como labelvaluebeans **/
	public static Object[] getSituationNameFiltersAsList() {
		return situationNameFilters.toArray();
	}

	/** retorna o nome do primeiro filtro com base no valor */
	public static String getFilterNameByValue(PrintAllCandidatesFilter filterBy) {
		switch (filterBy) {
		case FILTERBY_SPECIALIZATION_VALUE:
			return "Especializa��o";
		case FILTERBY_SITUATION_VALUE:
			return "Situa��o";
		case FILTERBY_DOESNTGIVESCLASSES_VALUE:
			return "N�o pretende dar aulas";
		case FILTERBY_GIVESCLASSES_VALUE:
			return"Pretende dar aulas";
		}
		return "Mostrar todos os registos";
	}
}