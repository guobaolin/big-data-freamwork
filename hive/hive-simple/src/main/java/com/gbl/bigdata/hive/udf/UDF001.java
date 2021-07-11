package com.gbl.bigdata.hive.udf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * @author: guobaolin
 * @date: 2021/6/30
 */
public class UDF001 extends GenericUDF {

    // 参数校验
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {

        if (objectInspectors.length != 1) {
            throw new UDFArgumentException("参数个数不为1");
        }

        return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    }

    // 处理数据
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {

        // 取出输入数据
        String input = deferredObjects[0].get().toString();
        if (StringUtils.isEmpty(input)) {
            return 0;
        }

        return input.length();
    }

    public String getDisplayString(String[] strings) {
        return null;
    }
}
