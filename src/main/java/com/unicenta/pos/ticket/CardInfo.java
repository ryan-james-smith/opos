
package com.unicenta.pos.ticket;

import com.unicenta.basic.BasicException;
import com.unicenta.data.loader.DataRead;
import com.unicenta.data.loader.IKeyed;
import com.unicenta.data.loader.SerializerRead;

public class CardInfo implements IKeyed {

    private String m_sID;
    private String m_sCardName;
    private double m_Charges;

    /** Creates new CategoryInfo */
    public CardInfo(String id, String name, double charges) {
        m_sID = id;
        m_sCardName = name;
        m_Charges = charges;
    }

    @Override
    public Object getKey() {
        return m_sID;
    }

    public void setID(String sID) {
        m_sID = sID;
    }

    public String getID() {
        return m_sID;
    }

    public String getName() {
        return m_sCardName;
    }

    public void setName(String sName) {
        m_sCardName = sName;
    }

    @Override
    public String toString() {
        return m_sCardName;
    }

    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {@Override
 public Object readValues(DataRead dr) throws BasicException {
            return new CardInfo(dr.getString(1), dr.getString(2), dr.getDouble(3));
        }};
    }


    /**
     * @return the m_Charges
     */
    public double getM_Charges() {
        return m_Charges;
    }

    /**
     * @param m_Charges the m_Charges to set
     */
    public void setM_Charges(double m_Charges) {
        this.m_Charges = m_Charges;
    }
}
