package org.openl.rules.calc.element;

import java.lang.reflect.Array;

import org.openl.binding.impl.NodeDescriptionHolder;
import org.openl.binding.impl.cast.IOpenCast;
import org.openl.rules.calc.ASpreadsheetField;
import org.openl.rules.calc.SpreadsheetResultCalculator;
import org.openl.types.IOpenClass;
import org.openl.vm.IRuntimeEnv;

public class SpreadsheetRangeField extends ASpreadsheetField implements NodeDescriptionHolder {

    private final SpreadsheetCellField fstart;
    private final SpreadsheetCellField fend;
    private final IOpenCast[][] casts;

    public SpreadsheetRangeField(String name,
            SpreadsheetCellField fstart,
            SpreadsheetCellField fend,
            IOpenClass rangeType,
            IOpenCast[][] casts) {
        super(fstart.getDeclaringClass(), name, rangeType.getArrayType(1));
        this.fstart = fstart;
        this.fend = fend;
        this.casts = casts;
    }

    @Override
    public Object get(Object target, IRuntimeEnv env) {
        int sx = fstart.getCell().getColumnIndex();
        int sy = fstart.getCell().getRowIndex();
        int ex = fend.getCell().getColumnIndex();
        int ey = fend.getCell().getRowIndex();

        int w = ex - sx + 1;
        int h = ey - sy + 1;

        int size = w * h;

        SpreadsheetResultCalculator calc = (SpreadsheetResultCalculator) target;
        Class<?> to = getType().getComponentClass().getInstanceClass();
        Object array = Array.newInstance(to, size);
        int i = 0;
        for (int x = sx; x <= ex; ++x) {
            for (int y = sy; y <= ey; ++y) {
                Object v = calc.getValue(y, x);
                IOpenCast openCast = casts[x - sx][y - sy];
                if (openCast != null && openCast.isImplicit()) {
                    v = openCast.convert(v);
                }
                Array.set(array, i, v);
                i++;
            }
        }

        return array;
    }

    @Override
    public String getDescription() {
        return getType().getDisplayName(SHORT) + " " + fstart.getName() + ":" + fend.getName();
    }
}
