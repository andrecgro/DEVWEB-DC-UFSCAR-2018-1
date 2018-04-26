/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.consultas.forms;

import br.ufscar.dc.consultas.beans.Medico;
import br.ufscar.dc.consultas.beans.Paciente;
import java.util.Date;

/**
 *
 * @author andrerocha
 */
public class CadastrarConsultaForm {
    private String crm;
    String DataExame;
    
    public String getDataExame() {
        return DataExame;
    }

    public void setDataExame(String DataExame) {
        this.DataExame = DataExame;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

}
