//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2018 uniCenta & previous Openbravo POS works
//    https://unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.unicenta.data.gui;

import com.unicenta.data.loader.IKeyGetter;
import com.unicenta.data.loader.KeyGetterBuilder;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author  adrian
 */
public class ComboBoxValModel<E> extends AbstractListModel<E> implements ComboBoxModel<E> {

    private List<E> m_aData;
    private IKeyGetter m_keygetter;
    private E m_selected;

    /** Creates a new instance of ComboBoxValModel
     * @param aData
     * @param keygetter */
    public ComboBoxValModel(List<E> aData, IKeyGetter keygetter) {
        m_aData = aData;
        m_keygetter = keygetter;
        m_selected = null;
    }

    /**
     *
     * @param aData
     */
    public ComboBoxValModel(List<E> aData) {
        this(aData, KeyGetterBuilder.INSTANCE);
    }

    /**
     *
     * @param keygetter
     */
    public ComboBoxValModel(IKeyGetter keygetter) {
        this(new ArrayList<>(), keygetter);
    }

    /**
     *
     */
    public ComboBoxValModel() {
        this(new ArrayList<>(), KeyGetterBuilder.INSTANCE);
    }

    /**
     *
     * @param c
     */
    public void add(E c) {
        m_aData.add(c);
    }

    /**
     *
     * @param c
     */
    public void del(E c) {
        m_aData.remove(c);
    }

    /**
     *
     * @param index
     * @param c
     */
    public void add(int index, E c) {
        m_aData.add(index, c);
    }

    /**
     *
     * @param aData
     */
    public void refresh(List<E> aData) {
        m_aData = aData;
        m_selected = null;
    }

    /**
     *
     * @return
     */
    public Object getSelectedKey() {
        if (m_selected == null) {
            return null;
        } else {
            return m_keygetter.getKey(m_selected);  // Si casca, excepcion parriba
        }
    }

    /**
     *
     * @return
     */
    public String getSelectedText() {
        if (m_selected == null) {
            return null;
        } else {
            return m_selected.toString();
        }
    }

    /**
     *
     * @param aKey
     */
    public void setSelectedKey(E aKey) {
        setSelectedItem(getElementByKey(aKey));
    }

    /**
     *
     */
    public void setSelectedFirst() {
        m_selected = (m_aData.isEmpty()) ? null : m_aData.get(0);
    }

    /**
     *
     * @param aKey
     * @return
     */
    public E getElementByKey(E aKey) {
        if (aKey != null) {
            for (E value : m_aData) {
                if (aKey.equals(m_keygetter.getKey(value))) {
                    return value;
                }
            }
        }
        return null;
    }

    @Override
    public E getElementAt(int index) {
        return m_aData.get(index);
    }

    @Override
    public E getSelectedItem() {
        return m_selected;
    }

    @Override
    public int getSize() {
        return m_aData.size();
    }

    @Override
    public void setSelectedItem(Object anItem) {

        if ((m_selected != null && !m_selected.equals(anItem)) || m_selected == null && anItem != null) {
            m_selected = (E) anItem;
            fireContentsChanged(this, -1, -1);
        }
    }

}
