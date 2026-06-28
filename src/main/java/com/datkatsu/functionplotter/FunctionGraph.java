package com.datkatsu.functionplotter;

import com.datkatsu.expressionparser.ExpressionProcessor;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/***
 * Class that contains the graph of a mathematical expression. It has a list of points.
 */

public class FunctionGraph
{
    private String expression;
    private Point2D.Double points[];
    private ExpressionProcessor expressionProcessor = new ExpressionProcessor();
    private int minX, maxX;
    private int count;
    
    public FunctionGraph(String expression, int minX, int maxX, int count)
    {
        this.expression = expression;
        this.minX = minX;
        this.maxX = maxX;
        this.count = count;
        this.points = new Point2D.Double[count];
        calculate ();
    }
    
    private void calculate()
    {
        Map<String, Double> variableTable = new HashMap<String, Double>();
        double range = maxX - minX;
        for(int i = 0 ; i < count; i++)
        {
            double x = minX + (i * range / (count - 1));
            variableTable.put ("x", x);
            double y = expressionProcessor.process(expression, variableTable);
            points[i] = new Point2D.Double(x, y);
        }
    }
    
    public void update()
    {
        calculate ();
    }
    
    public Point2D.Double[] getPoints()
    {
        return points;
    }
    
    public int size() {
        return points.length;
    }
}
