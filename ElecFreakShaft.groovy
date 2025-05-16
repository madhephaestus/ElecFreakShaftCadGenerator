import eu.mihosoft.vrl.v3d.parametrics.*;
import java.util.stream.Collectors;
import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.CSG;
import eu.mihosoft.vrl.v3d.Cube;
import eu.mihosoft.vrl.v3d.Cylinder
CSG generate(){
	String type= "ElecFreakShaft"
	if(args==null)
		args=["40mm"]
	// The variable that stores the current size of this vitamin
	StringParameter size = new StringParameter(	type+" Default",args.get(0),Vitamins.listVitaminSizes(type))
	HashMap<String,Object> measurments = Vitamins.getConfiguration( type,size.getStrValue())

	def diameterValue = measurments.diameter
	def insetValue = measurments.inset
	def massCentroidXValue = measurments.massCentroidX
	def massCentroidYValue = measurments.massCentroidY
	def massCentroidZValue = measurments.massCentroidZ
	def massKgValue = measurments.massKg
	def priceValue = measurments.price
	def sourceValue = measurments.source
	def widthOfFinValue = measurments.widthOfFin
	def lengthOfShaftValue=measurments.lengthOfShaft

	CSG shaft = new Cylinder(diameterValue/2,lengthOfShaftValue).toCSG()
	CSG cutter = new Cube(diameterValue, widthOfFinValue-0.2, lengthOfShaftValue).toCSG().toZMin()
	CSG xy = shaft.intersect(cutter);
	CSG yx = shaft.intersect(cutter.rotz(90));
	
	return xy.union(yx)
		.setParameter(size)
		.setRegenerate({generate()})
}
return generate() 