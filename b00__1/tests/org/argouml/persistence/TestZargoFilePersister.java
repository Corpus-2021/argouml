// $Id:TestZargoFilePersister.java 12987 2007-07-05 14:28:59Z mvw $
// Copyright (c) 1996-2007 The Regents of the University of California. All
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

package org.argouml.persistence;

import java.io.File;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.argouml.kernel.Project;
import org.argouml.model.InitializeModel;
import org.argouml.notation.InitNotation;
import org.argouml.notation.providers.java.InitNotationJava;
import org.argouml.notation.providers.uml.InitNotationUml;

/**
 * Testcase to load projects without exception.
 */
public class TestZargoFilePersister extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        (new InitNotation()).init();
        (new InitNotationUml()).init();
        (new InitNotationJava()).init();
    }

    /**
     * The constructor.
     *
     * @param name the name
     */
    public TestZargoFilePersister(String name) {
        super(name);
        InitializeModel.initializeDefault();
    }

    /**
     * @param args the arguments given on the commandline
     */
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    /**
     * @return the test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(TestZargoFilePersister.class);

        return suite;
    }

    /**
     * Tests that a project is loadable.
     *
     * @param filename of the project file to load
     * @throws OpenException if something goes wrong.
     */
    private Project doLoad(String filename) 
        throws OpenException, InterruptedException {
        URL url = TestZargoFilePersister.class.getResource(filename);
        assertTrue("Unintended failure: resource to be tested is not found: "
                + filename + ", converted to URL: " + url, url != null);
        ZargoFilePersister persister = new ZargoFilePersister();
        String name = url.getFile();
        Project p = persister.doLoad(new File(name));
        assertTrue("Load Status for " + filename + ".",
               PersistenceManager.getInstance().getLastLoadStatus());
        return p;
    }

    /**
     * Test loading an old empty zargo.
     *
     * @throws Exception when e.g. the file is not found
     */
    public void testDoLoad1() throws Exception {
        doLoad("/testmodels/uml13/Empty.zargo");
    }

    /**
     * Test loading a new empty zargo.
     *
     * @throws Exception when e.g. the file is not found
     */
    public void testDoLoad1a() throws Exception {
        doLoad("/testmodels/uml14/EmptyProject024.zargo");
    }

    /**
     * Test loading UML 1.3 which needs to be upgraded on load zargo.
     *
     * @throws Exception when e.g. the file is not found
     */
    public void testDoLoad2() throws Exception {
        doLoad("/testmodels/uml13/Alittlebitofeverything.zargo");
    }

    /**
     * Test loading a native UML 1.4 zargo.
     *
     * @throws Exception when e.g. the file is not found
     */
    public void testDoLoad2a() throws Exception {
        doLoad("/testmodels/uml14/Alittlebitofeverything.zargo");
    }

    
    /**
     * Test saving a zargo.
     *
     * @throws Exception when e.g. the file is not found
     */
    public void testSave() throws Exception {
        Project p = doLoad("/testmodels/uml13/Alittlebitofeverything.zargo");
        ZargoFilePersister persister = new ZargoFilePersister();
        persister.save(p, new File("Alittlebitofeverything2.zargo"));
    }

    /**
     * Test loading some garbage in a zargo.
     */
    public void testLoadGarbage() {
        File file = null;
        boolean loaded = true;
        try {
            file = new File("/testmodels/Garbage.zargo");
            ZargoFilePersister persister = new ZargoFilePersister();
            persister.doLoad(file);
            assertTrue("Load Status",
                    !PersistenceManager.getInstance().getLastLoadStatus());
        } catch (OpenException io) {
            // This is the normal case.
            loaded = false;
        } catch (InterruptedException iExc) {
            // This should not happen!
            loaded = false;
        }
        assertTrue("No exception was thrown.", !loaded);
    }
}



