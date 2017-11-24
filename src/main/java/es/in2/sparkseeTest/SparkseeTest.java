/**
 * 
 */
package es.in2.sparkseeTest;

import java.io.FileNotFoundException;

import com.sparsity.sparksee.gdb.Attribute;
import com.sparsity.sparksee.gdb.AttributeKind;
import com.sparsity.sparksee.gdb.AttributeList;
import com.sparsity.sparksee.gdb.AttributeListIterator;
import com.sparsity.sparksee.gdb.Condition;
import com.sparsity.sparksee.gdb.DataType;
import com.sparsity.sparksee.gdb.Database;
import com.sparsity.sparksee.gdb.Graph;
import com.sparsity.sparksee.gdb.Objects;
import com.sparsity.sparksee.gdb.Session;
import com.sparsity.sparksee.gdb.Sparksee;
import com.sparsity.sparksee.gdb.SparkseeConfig;
import com.sparsity.sparksee.gdb.TextStream;
import com.sparsity.sparksee.gdb.Type;
import com.sparsity.sparksee.gdb.TypeList;
import com.sparsity.sparksee.gdb.TypeListIterator;
import com.sparsity.sparksee.gdb.Value;

/**
 * @author joseg
 *
 */
public class SparkseeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SparkseeConfig cfg = new SparkseeConfig();
		cfg.setCacheMaxSize(2048); // 2 GB
		cfg.setLogFile("C:\\bdDePruebaSparksee\\logs\\HelloSparksee.log");
		Sparksee sparksee = new Sparksee(cfg);
		try {
			Database db = sparksee.create("C:\\bdDePruebaSparksee\\HelloSparksee2.gdb", "HelloSparksee");
			Session sess = db.newSession();
	        Graph graph = sess.getGraph();
	        // Use 'graph' to perform operations on the graph database
	        int peopleTypeId = graph.newNodeType("PEOPLE");
	        int friendTypeId = graph.newEdgeType("FRIEND", true, true);
	        
	        long people1 = graph.newNode(peopleTypeId);
	        long people2 = graph.newNode(peopleTypeId);
	        long friend1 = graph.newEdge(friendTypeId, people1, people2);
	        String str1 = "This is the first chunk of the text stream";
	        String str2 = "This is the second chunk of the text stream";
	        
	        int nameAttrId = graph.findAttribute(peopleTypeId, "Name");
	        if (Attribute.InvalidAttribute == nameAttrId)
	        {
	            nameAttrId = graph.newAttribute(peopleTypeId, "Name", DataType.String, AttributeKind.Indexed);
	        }
	        
	        Value v = new Value();
	        graph.setAttribute(people1, nameAttrId, v.setString("Scarlett Johansson"));
	        graph.setAttribute(people2, nameAttrId, v.setString("Woody Allen"));

	        /*int textAttrId = graph.findAttribute(peopleTypeId, "text");
	        if (Attribute.InvalidAttribute == textAttrId)
	        {
	        	textAttrId = graph.newAttribute(peopleTypeId, "Text", DataType.String, AttributeKind.Basic);
	        }
	        TextStream tstrm = new TextStream(false);
	        graph.setAttributeText(people1, textAttrId, tstrm);
	        char[] buff = str1.toCharArray();
	        tstrm.write(buff, buff.length);
	        buff = str2.toCharArray();
	        tstrm.write(buff, buff.length);
	        tstrm.close();*/
	        
	       
	
	        // retrieve all 'people' node objects
	        Objects peopleObjs1 = graph.select(peopleTypeId);
	      
	        // retrieve Scarlett Johansson from the graph, which is a "PEOPLE" node
	        Objects peopleObjs2 = graph.select(nameAttrId, Condition.Equal, v.setString("Scarlett Johansson"));
	       
	        // retrieve all 'PEOPLE' node objects having "Allen" in the name. It would retrieve
	        // Woody Allen, Tim Allen or Allen Leech, or other similar if they are present in the graph. 
	        Objects peopleObjs3 = graph.select(nameAttrId, Condition.Like, v.setString("Allen"));
	        
	        peopleObjs1.close();
	        peopleObjs2.close();
	        peopleObjs3.close();
	        
	        peopleTypeId = graph.findType("people");
	        if (Type.InvalidType == peopleTypeId)
	        {
	            peopleTypeId = graph.newNodeType("people");
	        }
	        friendTypeId = graph.findType("friend");
	        if (Type.InvalidType == friendTypeId)
	        {
	            friendTypeId = graph.newEdgeType("friend", true, true);
	        }
	       
	        TypeList tlist = graph.findEdgeTypes();
	        TypeListIterator tlistIt = tlist.iterator();
	        /*************/
	        
	        /*************/
	        while (tlistIt.hasNext())
	        {
	            int type = tlistIt.next();
	            Type tdata = graph.getType(type);
	            System.out.println("Type " + tdata.getName() + " with " + tdata.getNumObjects() + " objects");
	                    
	            AttributeList alist = graph.findAttributes(type);
	            AttributeListIterator alistIt = alist.iterator();
	            while (alistIt.hasNext())
	            {
	                int attr = alistIt.next();
	                Attribute adata = graph.getAttribute(attr);
	                System.out.println(" - Attribute " + adata.getName());
	                        
	                graph.removeAttribute(attr);
	            }
	                    
	            graph.removeType(type);
	        }
	        
	        //db.setCacheMaxSize(db.getCacheMaxSize()/2);
	        sess.close();
	        db.close();
	        sparksee.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sparksee.close();
		}

	}

}
