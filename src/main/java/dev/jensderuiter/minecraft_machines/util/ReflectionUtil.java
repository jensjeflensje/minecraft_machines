package dev.jensderuiter.minecraft_machines.util;

import dev.jensderuiter.minecraft_machines.MachinesPlugin;
import dev.jensderuiter.minecraft_machines.machine.BaseMachine;
import dev.jensderuiter.minecraft_machines.machine.Machine;
import dev.jensderuiter.minecraft_machines.machine.RegisterMachine;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtil {

    public static void registerMachineTypes() {
        Set<Class<?>> machineClasses = new Reflections("dev.jensderuiter.minecraft_machines.machine")
                .getTypesAnnotatedWith(RegisterMachine.class);
        for (Class<?> clazz : machineClasses) {
            try {
                Method nameMethod = clazz.getDeclaredMethod("getName");
                String name = (String) nameMethod.invoke(null);
                if (name == null) {
                    throw new RuntimeException(clazz.getSimpleName() + " doesn't override a getName() function.");
                }
                MachinesPlugin.machineTypes.put(name, (Class<? extends BaseMachine>) clazz);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(clazz.getSimpleName() + " doesn't override a getName() function.");
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
