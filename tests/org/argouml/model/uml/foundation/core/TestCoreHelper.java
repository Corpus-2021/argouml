// $Id$
// Copyright (c) 1996-99 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

// $header$
package org.argouml.model.uml.foundation.core;

import junit.framework.TestCase;

import org.argouml.application.security.ArgoSecurityManager;
import org.argouml.model.uml.UmlFactory;
import org.argouml.util.CheckUMLModelHelper;

import ru.novosoft.uml.foundation.core.MClassifierImpl;
import ru.novosoft.uml.foundation.core.MGeneralizableElement;
import ru.novosoft.uml.foundation.core.MGeneralization;
import ru.novosoft.uml.foundation.core.MGeneralizationImpl;

/**
 * @since Oct 10, 2002
 * @author jaap.branderhorst@xs4all.nl
 */
public class TestCoreHelper extends TestCase {

    /**
     * Constructor for TestCoreHelper.
     * @param arg0
     */
    public TestCoreHelper(String arg0) {
	super(arg0);
    }

    public void testGetMetaModelName() {
	CheckUMLModelHelper.metaModelNameCorrect(
						 this,
						 CoreFactory.getFactory(),
						 TestCoreFactory.allModelElements);
    }

    public void testIsValidStereoType() {
	CheckUMLModelHelper.isValidStereoType(
					      this,
					      CoreFactory.getFactory(),
					      TestCoreFactory.allModelElements);
    }

    public void testGetChildren() {
	// Create an element with an element without children.
	MGeneralizableElement ge = new MClassifierImpl();

	assertTrue(CoreHelper.getHelper().getChildren(ge).size() == 0);

	// Add one child.
	MGeneralization g1 = new MGeneralizationImpl();
	g1.setParent(ge);
	g1.setChild(new MClassifierImpl());

	assertTrue(CoreHelper.getHelper().getChildren(ge).size() == 1);

	// Add another child.
	MGeneralization g2 = new MGeneralizationImpl();
	g2.setParent(ge);
	MGeneralizableElement ge2 = new MClassifierImpl();
	g2.setChild(ge2);

	assertTrue(CoreHelper.getHelper().getChildren(ge).size() == 2);

	// Add grandchild.
	MGeneralization g3 = new MGeneralizationImpl();
	g3.setParent(ge2);
	g3.setChild(new MClassifierImpl());

	assertTrue(CoreHelper.getHelper().getChildren(ge).size() == 3);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
	super.setUp();
	ArgoSecurityManager.getInstance().setAllowExit(true);
        UmlFactory.getFactory().setGuiEnabled(false);
    }
}
