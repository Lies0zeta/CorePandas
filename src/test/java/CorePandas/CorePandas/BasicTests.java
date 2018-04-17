package CorePandas.CorePandas;

import CorePandas.CorePandas.data_structures.BadIndexException;
import CorePandas.CorePandas.data_structures.DataFrame;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BasicTests {
	private DataFrame df1;
	private DataFrame df2;
	@Before
	public void setUp() throws Exception {
		df1 = DataFrame.readCSV("files/taxables.csv");
		df2 = new DataFrame(
                Arrays.<String>asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                Arrays.<String>asList("Index", "Item", "Cost", "Tax", "Total"),
                Arrays.<List<? extends Object>>asList(
                        Arrays.<Integer>asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                        Arrays.<String>asList("Fruit of the Loom Girl's Socks", "Rawlings Little League Baseball",
                        		"Secret Antiperspirant", "Deadpool DVD", "Maxwell House Coffee 28 oz", "Banana Boat Sunscreen 8 oz",
                        		"Wrench Set 18 pieces", "M and M 42 oz", "Bertoli Alfredo Sauce", "Large Paperclips 10 boxes"),
                        Arrays.<Double>asList(7.97, 2.97, 1.29, 14.96, 7.28, 6.68, 10., 8.98, 2.12, 6.19),
                        Arrays.<Double>asList(0.6, 0.22, 0.1, 1.12, 0.55, 0.5, 0.75, 0.67, 0.16, 0.46),
                        Arrays.<Double>asList(8.57, 3.19, 1.39, 16.08, 7.83, 7.18, 10.75, 9.65, 2.28, 6.65)
                    )
            );
	}
	
	@Test(expected=BadIndexException.class)
    public final void testDuplicateColumnsInConstructor() {
        new DataFrame(Arrays.<Object>asList("test", "test"));
    }

	@Test
	public void testConstructor() {
        assertArrayEquals(
                "column names are correct",
                new Object[] {"Index", "Item", "Cost", "Tax", "Total"},
                df1.getColumnIndex().toArray()
            );
        assertArrayEquals(
                "column names are correct",
                new Object[] {"Index", "Item", "Cost", "Tax", "Total"},
                df2.getColumnIndex().toArray()
            );
        assertArrayEquals(
                "row names are correct",
                new Object[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
                df2.getRowIndex().toArray()
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                df1.getCol(0).toArray()
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                df2.getCol(0).toArray()
            );
	}
	
	@Test
    public final void testColByName() {
        final Object[] col1 = df1.getCol("Index").toArray();
        final Object[] col2 = df2.getCol("Index").toArray();
        
        assertArrayEquals(
                "data is correct",
                new Object[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                col1
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                col2
            );
        final Object[] col3 = df1.getCol("Total").toArray();
        final Object[] col4 = df2.getCol("Total").toArray();
        System.out.println(col3[0]+","+col3[1]+","+col3[2]);
        assertArrayEquals(
                "data is correct",
                new Object[] {8.57, 3.19, 1.39, 16.08, 7.83, 7.18, 10.75, 9.65, 2.28, 6.65},
                col3
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {8.57, 3.19, 1.39, 16.08, 7.83, 7.18, 10.75, 9.65, 2.28, 6.65},
                col4
            );
    }

}
