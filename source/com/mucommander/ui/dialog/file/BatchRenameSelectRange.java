/*
 * This file is part of muCommander, http://www.mucommander.com
 * Copyright (C) 2002-2008 Maxence Bernard
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mucommander.ui.dialog.file;

import com.mucommander.text.Translator;
import com.mucommander.ui.dialog.FocusDialog;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A dialog that allows select a range of characters from file name. 
 * Invoked from batch-rename dialog.
 *  
 * @author Mariusz Jakubowski
 *
 */
public class BatchRenameSelectRange extends FocusDialog implements ActionListener {

    private JTextField edtRange;
    private JButton btnCancel;
    private AbstractButton btnOK;
    private String range = null;
    

    public BatchRenameSelectRange(Dialog owner, String filename) {
        super(owner, Translator.get("batch_rename_dialog.title"), owner);
        edtRange = new JTextField();
        ReadOnlyDocument doc = new ReadOnlyDocument();
        edtRange.setDocument(doc);
        edtRange.setText(filename);
        edtRange.setColumns(filename.length() + 5);
        edtRange.setSelectionStart(0);
        edtRange.setSelectionEnd(filename.length());
        doc.setReadOnly(true);
        setLayout(new BorderLayout());            
        add(edtRange, BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout());
        btnOK = new JButton(Translator.get("ok"));
        btnOK.addActionListener(this);
        btns.add(btnOK);
        btnCancel = new JButton(Translator.get("cancel"));
        btnCancel.addActionListener(this);
        btns.add(btnCancel);
        add(btns, BorderLayout.SOUTH);
    }


    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnCancel) {
            dispose();
        } else if (source == btnOK) {
            range = "[N" + Integer.toString(edtRange.getSelectionStart()+1);
            if (edtRange.getSelectionEnd() > 0 && edtRange.getSelectionEnd() > edtRange.getSelectionStart()+1) {
                range += "-" + Integer.toString(edtRange.getSelectionEnd());
            }
            range += "]";
            dispose();
        }
    }

    /**
     * Returns a token with selected range.
     * @return
     */
    public String getRange() {
        return range;
    }
    
    /**
     * A document model that can be disabled for editing.
     * @author Mariusz Jakubowski
     *
     */
    private static class ReadOnlyDocument extends PlainDocument {
        private boolean readOnly = false;
        
        public void setReadOnly(boolean readOnly) {
            this.readOnly = readOnly;
        }
        
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (!readOnly) {
                super.insertString(offs, str, a);
            }
        }

        public void remove(int offs, int len)
                throws BadLocationException {
            if (!readOnly) {
                super.remove(offs, len);
            }
        }
    
    }
    
    
}