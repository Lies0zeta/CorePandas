package CorePandas.CorePandas;

import CorePandas.CorePandas.data_structures.BadIndexException;
import CorePandas.CorePandas.data_structures.DataFrame;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
                (Collection<String>)Arrays.<String>asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                (Collection<String>)Arrays.<String>asList("Index", "Item", "Cost", "Tax", "Total"),
                Arrays.<List<? extends Comparable<?>>>asList(
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
	
	@Test
    public final void testEmptyDataFrame() {
        new DataFrame();
    }
	
	@Test
    public final void testEmptyDataFrame2() {
        new DataFrame("StudentID", "Age", "Note");
    }
	
	@Test
    public final void testEmptyDataFrame3() {
        new DataFrame(df2.getRowIndex(),df2.getColumnIndex());
    }
	
	@Test
    public final void testDataFrame() {
        new DataFrame(Collections.<List<? extends Comparable<?>>>emptyList());
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
                df1.getCol(0)
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                df2.getCol(0)
            );
	}
	
	@Test
    public final void testColByName() {
        final Object[] col1 = df1.getCol("Index");
        final Object[] col2 = df2.getCol("Index");
        
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
        final Object[] col3 = df1.getCol("Total");
        final Object[] col4 = df2.getCol("Total");

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
	
	@Test
	public final void testColByIndex() {
        final Object[] col1 = df1.getCol(1);
        final Object[] col2 = df2.getCol(1);
        
        assertArrayEquals(
                "data is correct",
                new Object[] {"Fruit of the Loom Girl's Socks", "Rawlings Little League Baseball",
                		"Secret Antiperspirant", "Deadpool DVD", "Maxwell House Coffee 28 oz", "Banana Boat Sunscreen 8 oz",
                		"Wrench Set 18 pieces", "M and M 42 oz", "Bertoli Alfredo Sauce", "Large Paperclips 10 boxes"},
                col1
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {"Fruit of the Loom Girl's Socks", "Rawlings Little League Baseball",
                		"Secret Antiperspirant", "Deadpool DVD", "Maxwell House Coffee 28 oz", "Banana Boat Sunscreen 8 oz",
                		"Wrench Set 18 pieces", "M and M 42 oz", "Bertoli Alfredo Sauce", "Large Paperclips 10 boxes"},
                col2
            );
        final Object[] col3 = df1.getCol(2);
        final Object[] col4 = df2.getCol(2);

        assertArrayEquals(
                "data is correct",
                new Object[] {7.97, 2.97, 1.29, 14.96, 7.28, 6.68, 10., 8.98, 2.12, 6.19},
                col3
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {7.97, 2.97, 1.29, 14.96, 7.28, 6.68, 10., 8.98, 2.12, 6.19},
                col4
            );
    }
	
	@Test
    public final void testRowByIndex() {
        assertArrayEquals(
                "data is correct",
                new Object[] {1, "Fruit of the Loom Girl's Socks", 7.97, 0.6, 8.57},
                df1.getRow(0)
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {2, "Rawlings Little League Baseball", 2.97, 0.22, 3.19},
                df1.getRow(1)
            );
    }
	
	@Test
    public final void testRowByName() {
        assertArrayEquals(
                "data is correct",
                new Object[] {3, "Secret Antiperspirant", 1.29, 0.1, 1.39},
                df2.getRow("3")
            );
        assertArrayEquals(
                "data is correct",
                new Object[] {4, "Deadpool DVD", 14.96, 1.12, 16.08},
                df2.getRow("4")
            );
    }
	
	@Test
	public final void testSize() {
		assert(df1.size() == 5);
		assert(df2.size() == 5);
	}
}
